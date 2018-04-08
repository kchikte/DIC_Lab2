package com.mapper;
//source://https://github.com/jiankaidang/Relative-Frequencies-Hadoop/blob/master/src/Pairs.java
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.TreeSet;

/**
 * Author: Jiankai Dang
 * Date: 12/10/13
 */
public class Pairs {
    public static void main(String[] args) throws Exception {
        Job job = new Job(new Configuration());
        job.setJarByClass(Pairs.class);

        job.setNumReduceTasks(1);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Map.class);
        job.setCombinerClass(Combine.class);
        job.setReducerClass(Reduce.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
    	FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

    public static class Map extends Mapper<LongWritable, Text, Text, Text> {
	public static String[] punc_characters = {"\\?","x84","x99","x9f","xf0","x9d","xe2","x80","\\!","\\:","\\,","\\.","\\-","\\@","\\#","\\*","\\\\","xa6","x94","\\|","x9","x98"};
	public static String top_words="paulyoungryanhonorgunviolenceshootingyoutubepeoplevictimseveryonerightyetcarestudentsschoolsdemocratspartypercentdomesticyorkabuseconvictedfirearmslawanyoneandrewgovgunabusers";

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] words = value.toString().split(" ");

            for (String word : words) {
            	word=filter_punct_marks(word);
            	if(!isATopWord(word))
            		continue;
                if (word.matches("^\\w+$")) {
                    int count = 0;
                    for (String term : words) {
			term=filter_punct_marks(term);
                        if (term.matches("^\\w+$") && !term.equals(word)) {
                            context.write(new Text(word + "," + term), new Text("1"));
                            count++;
                        }
                    }
                }
            }
        }
    public static boolean isATopWord(String word) {
    		if(top_words.contains(word))
    			return true;
    		return false;
    }
	public static String filter_punct_marks(String string_word) {
	   for(String punct : punc_characters) {
		  
		   string_word=string_word.replaceAll(punct,"");
	   }
	   string_word=string_word.replaceAll("[^a-zA-Z0-9]", ""); // To replace all special characters from a word
	   string_word=string_word.toLowerCase();
	   return string_word;
   }
    }

    private static class Combine extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            int count = 0;
            for (Text value : values) {
                count += Integer.parseInt(value.toString());
            }
            context.write(key, new Text(String.valueOf(count)));
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {
        TreeSet<Pair> priorityQueue = new TreeSet<>();
        int totalCount = 0;

        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            String keyStr = key.toString();
            int count = 0;

            for (Text value : values) {
                count += Integer.parseInt(value.toString());
            }
            String[] pair = keyStr.split(",");
            priorityQueue.add(new Pair(count, pair[0], pair[1]));
            if (priorityQueue.size() > 20) {
                priorityQueue.pollFirst();
            }
        }

        protected void cleanup(Context context)
                throws IOException,
                InterruptedException {
            while (!priorityQueue.isEmpty()) {
                Pair pair = priorityQueue.pollLast();
                context.write(new Text(pair.key+" "+pair.value) ,new Text(pair.relativeFrequency));
            }
        }

        class Pair implements Comparable<Pair> {
            int relativeFrequency;
            String key;
            String value;

            Pair(int relativeFrequency, String key, String value) {
                this.relativeFrequency = relativeFrequency;
                this.key = key;
                this.value = value;
            }

            @Override
            public int compareTo(Pair pair) {
                if (this.relativeFrequency >= pair.relativeFrequency) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }
}
