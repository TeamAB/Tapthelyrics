package com.models;

import java.io.Serializable;

public class SongEntry implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String title;
	public String songFile;
	public String lyricsFile;
	public String artist;
	public int id;
	public String difficulty;
	
	public SongEntry()
	{
		
	}
	
	public SongEntry(int id, String title, String artist, String songFile, String lyricsFile, String difficulty)
	{
		this.id = id;
		this.artist = artist;
		this.title = title;
		this.songFile = songFile;
		this.lyricsFile = lyricsFile;
		this.difficulty = difficulty;
	}
	

}
