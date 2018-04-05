package com.d3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class D3Word {
	private String words = "var words = ["; //{text: 'have', size: 102,href: 'https://en.wikipedia.org/wiki/Beadle'},
	public D3Word(String filepath, String fileOutput) {
		try {
			FileReader fis = new FileReader(filepath);
			BufferedReader br = new BufferedReader(fis);
			String line="";
			StringTokenizer splitter;
			String word="";
			while((line=br.readLine())!=null) {
				splitter=new StringTokenizer(line);
				word=splitter.nextToken();
				words+="{text:"+"'"+word+"', size:"+splitter.nextToken()+", href: 'https://en.wikipedia.org/wiki/"+word+"'},";
			}
			words+="];";
			fis.close();
			br.close();
			File fr = new File("d3-wordcloud-master/example/"+fileOutput);
			BufferedWriter bw = new BufferedWriter(new FileWriter(fr));
			bw.write(words);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
