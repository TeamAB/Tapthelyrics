package com.project.tapthelyrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import com.db.DbHelper;
import com.db.Queries;
import com.db.Score;
import com.models.SongChunk;
import com.models.SongEntry;
import com.reader.Lyrics;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.database.sqlite.SQLiteDatabase;

public class PlayActivity3 extends Activity implements OnClickListener
{
	private MediaPlayer music;
	private List<SongChunk> listChunk;
	public int index = 0;    
    public TextView textLyrics, textOverlay;    
    public SongChunk prevChunk, currChunk;
    public Boolean isMusicPaused = false;
    public Button btn1, btn2, btn3;
    public String correctAnswer = "";
	public Timer timerStart, timerDelay;
	public String answerText = "";
	public int MAX_CHARS_DISPLAY = 30;
	public int showIndex = 0;
	public int scoreVal = 0;
	public int LOOP_COUNT = 7;
	public int nextIndex = 0;
	public int fromIndex = 0;
	
	public int SCORE_MULTIPLIER = 1;
	
	public TextView tvScore;
	
	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private Queries q;
	private Score score;
	private SongEntry entry;
	private Timer[] timers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		// remove the title bar, orientation always portrait, and while
		// playing the music the screen is always on
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//set the ui
		setContentView(R.layout.play);
		
		// initialize database
		dbHelper = new DbHelper(this);
		q = new Queries(db, dbHelper);
		
		// get the passed song entry
		entry = (SongEntry) this.getIntent().getSerializableExtra("entry");
		score = q.getScoreBySongId(entry.id);
		
		if(score == null)
			score = new Score();
		
		
		int maxQuestion = entry.difficulty.compareTo("easy") == 0 ? 10 : 15;
		
		//read lyrics from asset folder
//		listChunk = Lyrics.readLyrics(this, entry.lyricsFile, maxQuestion);
		listChunk = Lyrics.readLyrics2(this, entry.lyricsFile, maxQuestion);
		
		
		// initialization for reference
		textLyrics = (TextView) findViewById(R.id.textLyrics);
		textOverlay = (TextView) findViewById(R.id.textOverlay);
		
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvScore.setText("Score: 0");
		
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		
		// listeners for buttons
		// use to listen event when tapped.
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		
		
		// call update lyrics
		updateLyrics1();
		
		fromIndex = index;
		nextIndex += listChunk.size() > LOOP_COUNT ? LOOP_COUNT : listChunk.size();
		
		// play which music is selected
		playMusic(entry.songFile);
		
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
		// filter buttons to know which button id is tapped
		switch(v.getId())
		{
			case R.id.button1:
				checkAnswer(btn1.getText().toString());
				break;
				
			case R.id.button2:
				checkAnswer(btn2.getText().toString());
				break;
				
			case R.id.button3:
				checkAnswer(btn3.getText().toString());
				break;
		}
		
		enableButtons(false);
	}

	
	// when the user tapped the button, it play sounds if the boolean variable isCorrect is true
	// then it plays correct.ogg sound file, otherwise wrong.ogg is played
	public void playCorrectMusic(Boolean isCorrect)
	{
	    try 
	    {
	    	String file = "sfx/wrong.ogg";
	    	if(isCorrect)
	    		file = "sfx/correct.ogg";
	    	
	    	AssetFileDescriptor descriptor = this.getAssets().openFd(file);
		    long start = descriptor.getStartOffset();
		    long end = descriptor.getLength();
		    
		    MediaPlayer mp = new MediaPlayer();
		    
		    // set MediaPlayer settings by setting its volume, data source and preparing 
		    // its content before playing
		    mp.setVolume(1.0f, 1.0f);
		    mp.setDataSource(descriptor.getFileDescriptor(), start, end);
		    mp.prepare();
		    mp.start();
		} 
	    catch (IllegalArgumentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    catch (IllegalStateException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    catch (IOException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	}
	
	// function use to play the song music along with the lyrics sync
	public void playMusic(String fileName)
	{
	    try 
	    {
	    	// access the file in a certain folder and filename
//	    	AssetFileDescriptor descriptor = this.getAssets().openFd(fileName);
//		    long start = descriptor.getStartOffset();
//		    long end = descriptor.getLength();
		    
		    music = new MediaPlayer();
		    
		    // set MediaPlayer settings by setting its volume, data source and preparing 
		    // its content before playing
		    music.setVolume(1.0f, 1.0f);
//		    music.setDataSource(descriptor.getFileDescriptor(), start, end);
		    music.setDataSource(Environment.getExternalStorageDirectory() + "/" + fileName);
		    music.prepare();
			
		} 
	    catch (IllegalArgumentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    catch (IllegalStateException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    catch (IOException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    music.start();
	    initSong();
	    enableButtons(false);
	}
	
	
	// function use to syncronize both lyrics and the music file
	public void initSong()
	{
		timers = new Timer[listChunk.size()];
		for(int x = 0; x < listChunk.size(); x++)
		{
			final SongChunk chunk = listChunk.get(x);
			
//			long endSecLong = (long) (chunk.endSec);
			long startSecLong = (long) (chunk.startSec );
			
			long diffSec = 0;
			
			diffSec = startSecLong;
			
//			if(index == 0)
//			{
//				diffSec = (long) startSecLong;
//			}
			
			Log.e("diffSec ***", chunk.word + " = " + diffSec / 1000.0f);
			
			final int localX = x;
			
			timers[x] = new Timer();
			timers[x].schedule(new TimerTask() 
			{
		        @Override
		        public void run() 
		        {
		        	// thread safe integration so that the UI can be applied
		        	PlayActivity3.this.runOnUiThread(new Runnable() 
		        	{
						
						@Override
						public void run() 
						{
							// display highlighted text overlay, the red ones text
							String text = textOverlay.getText().toString() + chunk.word + " ";
//			            	textOverlay.setText(text);
			            	
			            	updateLyrics();
			            	
			            	++index;
			            	
			            	
			            	if(index < listChunk.size())
			            	{
			            		SongChunk chunk1 = listChunk.get(index);
				            	
				            	if(chunk1.isQuestion == 1)
				            	{
				            		prevChunk = chunk;
				            		currChunk = chunk1;
				            		setSelection();
				            	}
				            	
				            	// cancel timer, just to make sure timer is not existing
				            	timers[localX].purge();
				            	timers[localX].cancel();
			            	}
			            	else
			            	{
			            		// upon ending, the buttons were disabled and a certain duration of seconds
			            		// the popup will show.
			            		enableButtons(false);
			            		delayShowingEndGame();
			            	}
			            	
						}
					});
	            	
		        	
		        }

		    }, diffSec);
			
		}
	}
	
	// function use to delay the showing of end game
	// so that it will not terminate directly its music.
	private void delayShowingEndGame()
	{
		final Timer localTimer = new Timer();
		
		localTimer.schedule(new TimerTask() 
		{
	        @Override
	        public void run() 
	        {
	        	PlayActivity3.this.runOnUiThread(new Runnable() 
	        	{
					@Override
					public void run() 
					{
						// clean the timer and make sure it is cancel
						localTimer.purge();
						localTimer.cancel();
						
						endGame();
					}
				});
	        }

	    }, 2000);
	}

	
	// function use to display the popup, ask for name to user if it beats the 
	// highest score.
	private void endGame()
	{
		if(scoreVal == 0)
			this.finish();
		
		//check if the score value is greater than the existing value
		if(scoreVal > score.score)
		{
			LayoutInflater inflate = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View view = inflate.inflate(R.layout.dialog, null);
	 		
			// create dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle("New High Score");
	    	builder.setIcon(android.R.drawable.ic_dialog_info);
	    	builder.setView(view);
	    	builder.setCancelable(false);
	    	
	    	// set dialog button
	    	builder.setPositiveButton("Save", new DialogInterface.OnClickListener() 
		       {
		           public void onClick(DialogInterface dialog, int id) 
		           {
		        	   score.song_id = entry.id;
		        	   score.score = scoreVal;
		        	   
		        	   EditText txtName = (EditText) view.findViewById(R.id.txtName);
		        	   score.name = txtName.getText().toString();
		        	   
		        	   q.insertScore(score);
		        	   
		        	   PlayActivity3.this.finish();
		           }
		       })
		       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
		       {
		           public void onClick(DialogInterface dialog, int id) 
		           {
		                dialog.cancel();
		                PlayActivity3.this.finish();
		           }
		       });
	    	
	    	try
	    	{
	    		// show dialog
	    		AlertDialog alert = builder.create();
		    	alert.show();
	    	}
	    	catch(Exception e){}
		}
		else
		{
			this.finish();
		}
		
		
	}
	
	@Override
	protected void onDestroy() 
	{
		// when the activity is destroyed cancel all the music, timers to avoid 
		// exception
		super.onDestroy();
		
		if(music != null)
			music.stop();
		
		if(timerStart != null)
			timerStart.cancel();
		
		if(timerDelay != null)
			timerDelay.cancel();
		
		// stop remaining timers
		if(timers != null)
		{
			for(int x = 0; x < timers.length; x++)
			{
				timers[x].cancel();
			}
		}
	}


	@Override
	protected void onPause() 
	{
		// when the activity is destroyed cancel all the music, timers to avoid 
		// exception
		super.onPause();
		
		if(music != null)
			music.stop();
		
		if(timerStart != null)
			timerStart.cancel();
		
		if(timerDelay != null)
			timerDelay.cancel();
		
		// stop remaining timers
		if(timers != null)
		{
			for(int x = 0; x < timers.length; x++)
			{
				timers[x].cancel();
			}
		}
		
		this.finish();
	}
	
	// check if the answer is correct, if it is correct play the correct sound file, 
	// otherwise wrong sound file is played
	public void checkAnswer(String answer)
	{
		if(correctAnswer.compareToIgnoreCase(answer) == 0)
    	{
			answerText = "Correct!";
    		Log.e("ANSWER", "Correct");
    		
    		scoreVal += SCORE_MULTIPLIER;
    		tvScore.setText("Score: " + scoreVal);
    		playCorrectMusic(true);
    	}
    	else
    	{
    		answerText = "Wrong!";
    		Log.e("ANSWER", "Wrong");
    		playCorrectMusic(false);
    	}
		
//		Toast.makeText(this, answerText, Toast.LENGTH_SHORT).show();
		enableButtons(false);
	}
	
	
	// update the lyrics so that question lyrics indicated by "-" is changed to 
	// actual lyrics.
	private void updateLyrics()
	{
		if(index+ 1 == nextIndex)
		{
			fromIndex = index;
			nextIndex += listChunk.size() > LOOP_COUNT ? LOOP_COUNT : listChunk.size();
		}
		
		String text = "";
		int i = fromIndex;
		
		for(; i < nextIndex-1; i++)
		{
			if(i == listChunk.size())
				break;
			
			SongChunk c = listChunk.get(i);
			
			String empty = c.word;
			
			if(c.isQuestion == 1)
				empty = c.placeHolder;
			
			if(i <= index)
				empty = c.word;
			
//			if(text.length() <= MAX_CHARS_DISPLAY)
			text += empty + " ";
			
			if(i <= index)
				textOverlay.setText(text);
		
//			if(text.length() >= MAX_CHARS_DISPLAY && index == showIndex)
//			{
//				showIndex = i;
//				break;
//			}
			
//			if( i < showIndex && i != 0)
//				break;
			
			
		}
		
		textLyrics.setText(text);
	}
	
	
	private void updateLyrics1()
	{
		String text = "";
		int i = 0;
		
		for(; i < LOOP_COUNT-1; i++)
		{
			SongChunk c = listChunk.get(i);
			
			String empty = c.word;
			
			if(c.isQuestion == 1)
				empty = c.placeHolder;
			
			if(i <= index)
				empty = c.word;
			
			text += empty + " ";
			
//			if(text.length() >= MAX_CHARS_DISPLAY && index == showIndex)
//			{
//				showIndex = i;
//				break;
//			}
		}
		textLyrics.setText(text);
	}
	
	

	// toggle buttons if is enable or not to restric tap events
	private void enableButtons(Boolean isEnabled)
	{
		btn1.setEnabled(isEnabled);
		btn2.setEnabled(isEnabled);
		btn3.setEnabled(isEnabled);
		
		if(!isEnabled)
		{
			btn1.setText("");
			btn2.setText("");
			btn3.setText("");
		}
	}
	
	
	// function that sets the button selection for correct answer
	public void setSelection()
	{
		enableButtons(true);
		
		// get the correct answer
		String strCorrect = currChunk.word.replace("\n", "");
		correctAnswer = strCorrect;
		
		Random rand = new Random();
		int index1 = rand.nextInt(listChunk.size());
		int index2 = rand.nextInt(listChunk.size());
		
		// get the random answers
		String sel1 = currChunk.word.replace("\n", "").toLowerCase();
		String sel2 = listChunk.get(index1).word.replace("\n", "").toLowerCase();
		String sel3 = listChunk.get(index2).word.replace("\n", "").toLowerCase();
		
		// iterate over the answer to ensure no answer is duplicate values
		while(sel2.compareTo(sel3) == 0 || sel2.compareTo(sel1) == 0 || sel3.compareTo(sel1) == 0)
		{
			index2 = rand.nextInt(listChunk.size());
			sel3 = listChunk.get(index2).word.replace("\n", "").toLowerCase();
			
			index1 = rand.nextInt(listChunk.size());
			sel2 = listChunk.get(index1).word.replace("\n", "").toLowerCase();
		}
		
		
		// assign the answer to list to perform shuffle
		List<String> answers = new ArrayList<String>();
		answers.add(sel1);
		answers.add(sel2);
		answers.add(sel3);
		
		//shuffle answers
		Collections.shuffle(answers);
		
		// set the shuffled answer to the buttons
		btn1.setText(answers.get(0));
		btn2.setText(answers.get(1));
		btn3.setText(answers.get(2));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public void checkDelay(final SongChunk pChunk, final SongChunk cChunk)
//	{
//		long endSecLong = (long) (pChunk.endSec );
//		long startSecLong = (long) (cChunk.startSec );
//		
//		
//		long diffSec = (long) (startSecLong - endSecLong);
//		
//		if(diffSec > 1000)
//			diffSec -= 100;
//		
//		Log.e("DELAY", diffSec + " - " + cChunk.word);
//		
//		if(diffSec == 0.0f)
//		{
//			startSong(cChunk);
//		}
//		else
//		{
//			if(diffSec > 0)
//			{
//				timerDelay = new Timer();
//				timerDelay.schedule(new TimerTask() 
//				{
//			        @Override
//			        public void run() 
//			        {
//			        	timerDelay.cancel();
//						startSong(cChunk);
//			        }
//
//			    }, diffSec);	
//			}
//		}
//		
//		
//	}
	
//	public void startSong(final SongChunk chunk)
//	{
//		if(index < listChunk.size())
//		{
//			long endSecLong = (long) (chunk.endSec);
//			long startSecLong = (long) (chunk.startSec );
//			
//			long diffSec = 0;
//			
//			diffSec = (long) (endSecLong - startSecLong);
//			
//			if(index == 0)
//			{
//				diffSec = (long) startSecLong;
//			}
//			
//			Log.e("diffSec ***", chunk.word + " = " + diffSec / 1000.0f);
//			
//			timerStart = new Timer();
//			
//			timerStart.schedule(new TimerTask() 
//			{
//		        @Override
//		        public void run() 
//		        {
//		        	PlayActivity3.this.runOnUiThread(new Runnable() 
//		        	{
//						
//						@Override
//						public void run() 
//						{
//							// TODO Auto-generated method stub
//							String text = textOverlay.getText().toString() + chunk.word + " ";
//			            	textOverlay.setText(text);
//			            	
//			            	updateLyrics();
//			            	
//			            	++index;
//			            	if(index == listChunk.size())
//			            		return;
//			            	
//			            	SongChunk chunk1 = listChunk.get(index);
//			            	
//			            	if(chunk1.isQuestion == 1)
//			            	{
//			            		prevChunk = chunk;
//			            		currChunk = chunk1;
//			            		setSelection();
//			            	}
//			            	
//			            	timerStart.purge();
//			            	timerStart.cancel();
//			            	
//			            	if(chunk1.startSec - chunk.endSec > 0)
//			            	{
////			            		chunk1.endSec += (chunk1.startSec - chunk.endSec) + 60;
//			            		
//			            		checkDelay(chunk, chunk1);
//			            	}
//			            		
//			            	else
//			            		startSong(chunk1);
//			            	
//						}
//					});
//	            	
//		        	
//		        }
//
//		    }, diffSec);
//		    
//		}
//		else
//		{
//			index = 0;
//			music.stop();
//		}
//	}
	
}
