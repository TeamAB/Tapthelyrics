package com.project.tapthelyrics;

import java.util.List;

import com.db.DbHelper;
import com.db.Queries;
import com.models.SongEntry;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends Activity implements OnClickListener
{

	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private Queries q;
	private List<SongEntry> dbSongList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		
		// initialize buttons
		Button btnStart = (Button) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(this);
		
		Button btnHighScore = (Button) findViewById(R.id.btnHighScore);
		btnHighScore.setOnClickListener(this);
		
		Button btnInstruction = (Button) findViewById(R.id.btnInstruction);
		btnInstruction.setOnClickListener(this);
		
		Button btnExit = (Button) findViewById(R.id.btnExit);
		btnExit.setOnClickListener(this);
		
		Button btnGetSongs = (Button) findViewById(R.id.btnGetSongs);
		btnGetSongs.setOnClickListener(this);
		
		dbHelper = new DbHelper(this);
		q = new Queries(db, dbHelper);

		dbSongList = q.getAllSongs();
	}
	
	

	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		dbSongList = q.getAllSongs();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
 


	@Override
	public void onClick(View v) 
	{
		Intent i;
		
		// button ids to listen for tap events
		switch(v.getId())
		{
			case R.id.btnStart:
				if(dbSongList.size() == 0)
				{
					Toast.makeText(getApplicationContext(), 
							"Song List is empty. please click Get Song! to download songs.", Toast.LENGTH_SHORT).show();
					
					return;
				}
				
				i = new Intent(this, DifficultyActivity.class);
				startActivity(i);
				break;
				
			case R.id.btnHighScore:
				if(dbSongList.size() == 0)
				{
					Toast.makeText(getApplicationContext(), 
							"Song List is empty. please click Get Song! to download songs.", Toast.LENGTH_SHORT).show();
					
					return;
				}
				
				i = new Intent(this, HighScoreActivity.class);
				startActivity(i);
				break;
				
			case R.id.btnGetSongs:
				if(!isNetworkAvailable())
				{
					Toast.makeText(getApplicationContext(), 
							"No network connection.", Toast.LENGTH_SHORT).show();
					
					return;
				}
				
				i = new Intent(this, ServerListActivity.class);
				startActivity(i);
				break;
				
				
			case R.id.btnInstruction:
				
				i = new Intent(this, Instruction.class);
				startActivity(i);
				break;
				
			case R.id.btnExit:
				this.finish();
				break;
		}
	}
	
	private boolean isNetworkAvailable() {
		//checks connectivity of the app to the internet
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
