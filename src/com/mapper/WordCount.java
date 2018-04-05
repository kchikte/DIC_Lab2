//// Source : https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html
//import java.io.IOException;
//import java.util.StringTokenizer;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//
//public class WordCount {
//
//  public static class TokenizerMapper
//       extends Mapper<Object, Text, Text, IntWritable>{
//
//    private final static IntWritable one = new IntWritable(1);
//    private Text word = new Text();
//    public String[] punc_characters = {"\\?","x84","x99","x9f","xf0","x9d","xe2","x80","\\!","\\:","\\,","\\.","\\-","\\@","\\#","\\*","\\\\","xa6","x94","\\|","x9","x98"}; 
//    public String usr_tag_identifier="@";
//    public void map(Object key, Text value, Context context
//                    ) throws IOException, InterruptedException {
//      StringTokenizer itr = new StringTokenizer(value.toString());
//      String string_word="";
//      while (itr.hasMoreTokens()) {
//        word.set(itr.nextToken());
//        string_word=word.toString();
//        if(string_word.indexOf(usr_tag_identifier)!=0) {
//        		word=filter_punct_marks(word);
//        		context.write(word, one);
//        }
//      }
//    }
//   public Text filter_punct_marks(Text word) {
//	   String string_word = word.toString(); 
//	   for(String punct : punc_characters) {
//		  
//		   string_word=string_word.replaceAll(punct,"");
//	   }
//	   word = new Text(string_word);
//	   return word;
//   } 
//  }
//
//  public static class IntSumReducer
//       extends Reducer<Text,IntWritable,Text,IntWritable> {
//    private IntWritable result = new IntWritable();
//
//    public void reduce(Text key, Iterable<IntWritable> values,
//                       Context context
//                       ) throws IOException, InterruptedException {
//      int sum = 0;
//      for (IntWritable val : values) {
//        sum += val.get();
//      }
//      result.set(sum);
//      context.write(key, result);
//    }
//  }
//
//  public static void main(String[] args) throws Exception {
//    Configuration conf = new Configuration();
//    Job job = Job.getInstance(conf, "word count");
//    job.setJarByClass(WordCount.class);
//    job.setMapperClass(TokenizerMapper.class);
//    job.setCombinerClass(IntSumReducer.class);
//    job.setReducerClass(IntSumReducer.class);
//    job.setOutputKeyClass(Text.class);
//    job.setOutputValueClass(IntWritable.class);
//    FileInputFormat.addInputPath(job, new Path(args[0]));
//    FileOutputFormat.setOutputPath(job, new Path(args[1]));
//    System.exit(job.waitForCompletion(true) ? 0 : 1);
//  }
//}