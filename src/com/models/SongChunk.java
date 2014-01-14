package com.models;

public class SongChunk 
{
	public float startSec;
	public float endSec;
	public String word;
	public int isQuestion;
	
	public String placeHolder = "";
	
	public SongChunk(float startSec, float endSec, String word, int isQuestion)
	{
		this.startSec = startSec;
		this.endSec = endSec;
		this.word = word;
		this.isQuestion = isQuestion;
		
		for(int x = 0; x < word.length(); x++)
			placeHolder +="-";
	}
	
}
