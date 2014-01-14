package com.db;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper 
{
	static final String DB_NAME = "TapMyLyrics_db";
	static final int DB_VERSION = 1;
	static Activity activity;
	
	public DbHelper(Activity act) 
	{
		super(act.getApplicationContext(), DB_NAME, null, DB_VERSION);
		activity = act;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// create database
	    db.execSQL("CREATE TABLE IF NOT EXISTS scores("
	    		+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
	    		+ "name VARCHAR(255), "
	    		+ "score INT,"
	    		+ "song_id INT"
	    		+ ");");
	    
	    db.execSQL("CREATE TABLE IF NOT EXISTS songs("
	    		+ "song_id INTEGER, "
	    		+ "title VARCHAR(255), "
	    		+ "artist VARCHAR(255), "
	    		+ "songFile VARCHAR(255), "
	    		+ "lyricsFile VARCHAR(255), "
	    		+ "difficulty VARCHAR(255)"
	    		+ ");");
	}
	
	// Called whenever newVersion != oldVersion
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{ 
		//drop table if exist
		db.execSQL("DROP TABLE IF EXISTS scores");
	}
}