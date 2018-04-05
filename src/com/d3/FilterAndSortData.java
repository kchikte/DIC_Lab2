package com.d3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

public class FilterAndSortData {
	private String digits= "1,2,3,4,5,6,7,8,9,0";
	private int top_words_count=100;
	public FilterAndSortData(String filepath, String fileOutput,String sort_file) {
		try {
			String filtered_words="";
			ArrayList<Integer> words_list = new ArrayList<>();
			HashMap<String,String> count_map = new HashMap<>();
			FileReader fis = new FileReader(filepath);
			BufferedReader br = new BufferedReader(fis);
			String line="";
			StringTokenizer splitter;
			String word="";
			while((line=br.readLine())!=null) {
				splitter=new StringTokenizer(line);
				word=splitter.nextToken();
				if(isValidWord(word)) {
					filtered_words+=word+" "+splitter.nextToken()+"\n";
				}
			}
			fis.close();
			br.close();
			File fr = new File(fileOutput);
			BufferedWriter bw = new BufferedWriter(new FileWriter(fr));
			bw.write(filtered_words);
			bw.close();
			FileReader fir = new FileReader(fileOutput);
			BufferedReader bir = new BufferedReader(fir);
			String word1="";
			String count="";
			while((line=bir.readLine())!=null) {
//				System.out.println(line);
				if(line.trim()!="") {
					splitter=new StringTokenizer(line);
					word1=splitter.nextToken();
					count=splitter.nextToken();
					words_list.add(Integer.parseInt(count));
					count_map.put(count, word1);
				}
			}
//			System.out.println(words_list);
			Collections.sort(words_list);
//			System.out.println(words_list);
			int start_index=words_list.size()-1;
			int end_index=start_index-top_words_count;
			String sorted_words="";
			for(int index=start_index;index>end_index;index--) {
				count = ""+words_list.get(index);
//				System.out.println(count);
				word1 = count_map.get(count);
				sorted_words+=word1+" "+count+"\n";
			}
			File fur = new File(sort_file);
			BufferedWriter buw = new BufferedWriter(new FileWriter(fur));
			buw.write(sorted_words);
			buw.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public boolean isValidWord(String word) {
		boolean valid=true;
		String filtered_words="not";
		if(digits.indexOf(word.substring(0,1))>=0)
			return false;
		if(filtered_words.indexOf(word)>=0)
			return false;
		if(word.contains("https"))
			return false;
		return valid;
	}
}
