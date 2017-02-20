package com.sceptra.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Tagger {

	void tag() {
		String a = "I like watching movies";
		MaxentTagger tagger=null;
			
		try {
			tagger = new MaxentTagger(
					"/home/chiranz/Downloads/stanford-postagger-full-2011-05-18/models/left3words-distsim-wsj-0-18.tagger");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tagged = tagger.tagString(a);
		System.out.println(tagged);

	}

	public ArrayList<String> wordFilter(String Para) {
		ArrayList<String> wordList = new ArrayList<>();

		MaxentTagger tagger=null;
		try {
			tagger = new MaxentTagger(
					"/home/chiranz/Downloads/stanford-postagger-full-2011-05-18/models/left3words-distsim-wsj-0-18.tagger");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tagged = tagger.tagString(Para);
		System.out.println(tagged);

		String[] allWords = tagged.split(" ");

		for (int i = 0; i < allWords.length; i++) {
			System.out.println(allWords[i]);
			if (allWords[i].contains("/NN") || allWords[i].contains("/VB")) {

				String wordToStem=allWords[i].split("/")[0];

				String stemedword=new LancasterStemmer().stem(wordToStem);
				String stemmedWordWithTag=stemedword;//allWords[i].replace(wordToStem, stemedword);
				if(!wordList.contains(stemmedWordWithTag)) {
					wordList.add(stemmedWordWithTag);
				}
			}
		}
		System.out.println(wordList.toString());
		return wordList;

	}

	public static void main(String... a) {

		System.out.println(a.length);
		String para="";
		if(a.length==0){
		Scanner sc=new Scanner(System.in);
		para=sc.nextLine();
		
		}else {
			para=a[0];
			
		}
//		new Tagger().tag();

		new Tagger().getfreequency(new Tagger().wordFilter(para));

	
	}

	public Map<String, Integer> getfreequency(ArrayList<String> wordList) {

		Map<String, Integer> wordMap = new HashMap<>();

		for (int i = 0; i < wordList.size(); i++) {
			
			if(wordMap.get(wordList.get(i))==null){
				
				wordMap.put(wordList.get(i), 1);
			}else{
				
				Integer wordCount=wordMap.get(wordList.get(i));
				wordMap.replace(wordList.get(i), wordCount+1);
				
			}

		}
		System.out.println(wordMap.toString());

		return wordMap;
	}
}
