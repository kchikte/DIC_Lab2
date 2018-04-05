package com.d3;

public class WordCloudGenerator {
	private static String twitterfilepath="TwitterOutput/output1.txt";
	private static String nyfilepath="NewsOutput/output1.txt";
	private static String sort_tweet_file="top_50_tweet_words.txt";
	private static String sort_ny_file="top_50_news_words.txt";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new FilterAndSortData(twitterfilepath, "Filtered_tweet.txt",sort_tweet_file);
		new FilterAndSortData(nyfilepath, "Filtered_news.txt",sort_ny_file);
		new D3Word(sort_tweet_file, "twitter.words.js");
		new D3Word(sort_ny_file, "ny.words.js");
	}	

}
