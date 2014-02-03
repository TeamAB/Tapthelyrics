package com.db;

import java.util.ArrayList;

import com.models.SongEntry;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Queries 
{
	SQLiteDatabase db;
	DbHelper dbHelper;
	
	public Queries(SQLiteDatabase db, DbHelper dbHelper)
	{
		this.db = db;
		this.dbHelper = dbHelper;
	}
	
	//function use to insert a score to scores table
	public void insertScore(Score score)
	{
		db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues(); 
		values.put("name", score.name);
		values.put("score", score.score);
		values.put("song_id", score.song_id);
		
		db.insert("scores", null, values);
		db.close();
	}
	
	// fetch score by a certain song id
	public Score getScoreBySongId(int songId)
	{
		Score score = null;
		
		db = dbHelper.getReadableDatabase();
		Cursor mCursor = db.rawQuery("SELECT * FROM scores WHERE song_id = " + songId + " ORDER BY song_id ASC ", null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) 
		{
			do
			{
				score = new Score();
				score.id = mCursor.getInt(0);
				score.name = mCursor.getString(1);
				score.score = mCursor.getInt(2);
				score.song_id = mCursor.getInt(3);
				
			} while (mCursor.moveToNext());
		}
			
		mCursor.close();
		dbHelper.close();
		
		return score;
	}
	
	public void dropAllTableAndCreate()
	{
		db = dbHelper.getWritableDatabase();
		
		try
		{
		    db.delete("songs", null, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		db.close();
	}
	
	public void insertSong(SongEntry entry)
	{
		db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues(); 
		values.put("song_id", entry.id);
		values.put("artist", entry.artist);

		values.put("title", entry.title);
		values.put("lyricsFile", entry.lyricsFile);
		values.put("songFile", entry.songFile);
		values.put("difficulty", entry.difficulty);
		
		db.insert("songs", null, values);
		db.close();
	}
	
	// fetch score by a certain song id
	public ArrayList<SongEntry> getAllSongs()
	{
		ArrayList<SongEntry> songList = new ArrayList<SongEntry>();
		
		db = dbHelper.getReadableDatabase();
		Cursor mCursor = db.rawQuery("SELECT song_id, title, artist, lyricsFile, songFile, difficulty FROM songs", null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) 
		{
			do
			{
				SongEntry entry = new SongEntry();
				entry.id = mCursor.getInt(0);
				entry.title = mCursor.getString(1);
				entry.artist = mCursor.getString(2);
				entry.lyricsFile = mCursor.getString(3);
				entry.songFile = mCursor.getString(4);
				entry.difficulty = mCursor.getString(5);
				
				songList.add(entry);
				
			} while (mCursor.moveToNext());
		}
			
		mCursor.close();
		dbHelper.close();
		
		return songList;
	}
}
