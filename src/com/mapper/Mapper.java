package com.mapper; 

import java.util.*; 

import java.io.IOException; 

import org.apache.hadoop.fs.Path; 
import org.apache.hadoop.conf.*; 
import org.apache.hadoop.io.*; 
import org.apache.hadoop.mapred.*; 
import org.apache.hadoop.util.*;

public class WordCounter
{
	// Mapper class
	static class WordCounter_Map extends MapReduceBase implements Mapper{
          private final static IntWritable word_count_one = new IntWritable(1);
          private Text word = new Text();
	  public void map(LongWritable key, Text value, OutputCollector output, Reporter reporter) throws IOException{
				String line = value.toString();
				StringTokenizer sT = new StringTokenizer(line);
				while(sT.hasMoreTokens()) {
					word.set(sT.nextToken());
					output.collect(word,word_count_one);
					}
				}
		}
	static class WordCounter_Reduce extends MapReduceBase implements Reducer{
     	public void reduce(Text key, Iterator values, OutputCollector output,
		                Reporter reporter) throws IOException {
		 int sum = 0;
		 while (values.hasNext()) {
		                sum += values.next().get();
		 }
		 output.collect(key, new IntWritable(sum));
		}

	}
public static void main(String[] args) throws Exception {
	JobConf conf = new JobConf(WordCounter.class);
	conf.setJobName("WordCount");

	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(IntWritable.class);

	conf.setMapperClass(WordCounter_Map.class);
	//conf.setCombinerClass(Reduce.class);
	conf.setReducerClass(WordCounter_Reduce.class);

	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class);

	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));

	JobClient.runJob(conf);
}
}