package com.project.tapthelyrics;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;

public class DifficultyActivity extends Activity implements OnClickListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.difficulty);
		
		// initialize buttons
		Button btnEasy = (Button) findViewById(R.id.btnEasy);
		btnEasy.setOnClickListener(this);
		
		Button btnHard = (Button) findViewById(R.id.btnHard);
		btnHard.setOnClickListener(this);
		
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
			case R.id.btnEasy:
				i = new Intent(this, SongListActivity.class);
				i.putExtra("difficulty", "easy");
				startActivity(i);
				break;
				
			case R.id.btnHard:
				i = new Intent(this, SongListActivity.class);
				i.putExtra("difficulty", "hard");
				startActivity(i);
				break;
				
		}
	}

}
