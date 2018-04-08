package com.d3;

import java.io.File;

public class WordCloudGenerator {
	private static String twitterfilepath="TwitterOutput";
	private static String nyfilepath="NewsOutput";
	private static String sort_tweet_file="top_50_tweet_words.txt";
	private static String sort_ny_file="top_50_news_words.txt";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		new FilterAndSortData(twitterfilepath, "Filtered_tweet.txt",sort_tweet_file);
//		new FilterAndSortData(nyfilepath, "Filtered_news.txt",sort_ny_file);
//		new D3Word(sort_tweet_file, "twitter.words.js");
//		new D3Word(sort_ny_file, "ny.words.js");
		File output_dir = new File(twitterfilepath);
		File files[] = output_dir.listFiles();
		String file_output="";
		String sort_output="";
		String d3_word_file="";
		String d3_word_var="";
		for(File file : files) {
			file_output=file.getName();
			if(file_output.indexOf("DS_Store")>=0)
				continue;
			file_output=file_output.replace(".txt","");
			d3_word_var=file_output+"words";
			sort_output=file_output+"_topwords.txt";
			d3_word_file=file_output+"_d3.words.js";
			file_output=file_output+"_filtered.txt";
			new FilterAndSortData(twitterfilepath+"/"+file.getName(), file_output,sort_output);
			new D3Word(sort_output, d3_word_file,d3_word_var);
		}
		File ny_output_dir = new File(nyfilepath);
		File ny_files[] = ny_output_dir.listFiles();
		for(File file : ny_files) {
			
			file_output=file.getName();
			if(file_output.indexOf("DS_Store")>=0)
				continue;
			file_output=file_output.replace(".txt","");
			d3_word_var=file_output+"words";
			sort_output=file_output+"_topwords.txt";
			d3_word_file=file_output+"_d3.words.js";
			file_output=file_output+"_filtered.txt";
			new FilterAndSortData(nyfilepath+"/"+file.getName(), file_output,sort_output);
			new D3Word(sort_output, d3_word_file,d3_word_var);
		}
	}	

}
