package com.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.models.SongChunk;

public class Lyrics 
{
	public static List<SongChunk> readLyrics2(Context c, String fileName, int maxQuestion)
	{
		
		List<SongChunk> chunkList = new ArrayList<SongChunk>();
		
		File sdcard = Environment.getExternalStorageDirectory();

		//Get the text file
		File file = new File(sdcard , fileName);

		try {
		    
			BufferedReader bufferReader = new BufferedReader(new FileReader(file));
			
		    String str = "";
		    
		    while((str = bufferReader.readLine()) != null)
		    {
		        Log.e("***", str);
		        StringTokenizer st = new StringTokenizer(str);
		        
		        float startSec = Float.parseFloat( st.nextToken() ) * 1000.0f;
		        float endSec = Float.parseFloat( st.nextToken() ) * 1000.0f;
		        String word = st.nextToken();
		        
		        word = word.replace("-", " ");
		        
		        chunkList.add( new SongChunk( startSec, endSec, word, 0 ) );
//		        chunkList.add( new SongChunk( startSec, endSec, word, Integer.parseInt(isQuestion) ) );
		    }
		    
		    bufferReader.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return createTenQuestions(chunkList, 0, maxQuestion);
	}
	
	public static List<SongChunk> readLyrics(Context c,String fileName, int maxQuestion)
	{
		AssetManager manager = c.getResources().getAssets();
		
		InputStream stream = null;
		
		List<SongChunk> chunkList = new ArrayList<SongChunk>();
		
		try 
		{
			stream = manager.open(fileName);
			
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(stream));
			
		    String str = "";
		    
		    while((str = bufferReader.readLine()) != null)
		    {
		        Log.e("***", str);
		        StringTokenizer st = new StringTokenizer(str);
		        
		        float startSec = Float.parseFloat( st.nextToken() ) * 1000.0f;
		        float endSec = Float.parseFloat( st.nextToken() ) * 1000.0f;
		        String word = st.nextToken();
		        
		        word = word.replace("-", " ");
		        
		        chunkList.add( new SongChunk( startSec, endSec, word, 0 ) );
//		        chunkList.add( new SongChunk( startSec, endSec, word, Integer.parseInt(isQuestion) ) );
		    }
		    
		    bufferReader.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return createTenQuestions(chunkList, 0, maxQuestion);
	}
	
	
	// recursion use to create Ten question
	// for some reason some randoms fails to create 10 question, it calls again the function
	//and continue to create
	public static List<SongChunk> createTenQuestions(List<SongChunk> chunkList, int questionCount,  int maxQuestion)
	{
		SongChunk prevChunk = null;
		for(SongChunk chunk : chunkList)
		{
			Random rand = new Random();
			
			int Low = 1;
			int High = 10;
			int randomQ = rand.nextInt(High-Low) + Low;
			
			
			if(randomQ == 1 && questionCount < maxQuestion + 1)
			{
				if(prevChunk != null && prevChunk.isQuestion != 1)
				{
					Log.e("randomQ", ""+randomQ);
					chunk.isQuestion = randomQ;
					questionCount++;
				}
				
			}
			
			prevChunk = chunk;
		}
		
		if(questionCount < 10)
			createTenQuestions(chunkList, questionCount, maxQuestion);
		
		return chunkList;
	}

}
