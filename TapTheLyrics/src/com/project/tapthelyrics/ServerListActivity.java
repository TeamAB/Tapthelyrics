package com.project.tapthelyrics;

import java.util.ArrayList;
import java.util.List;

import com.adapters.ListAdapter;
import com.adapters.ListAdapter.OnListAdapterListener;
import com.db.DbHelper;
import com.db.Queries;
import com.json.Synchronize;
import com.json.Synchronize.OnSynchronizeListener;
import com.models.SongEntry;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;

public class ServerListActivity extends Activity implements OnItemClickListener, OnSynchronizeListener, OnListAdapterListener
{
	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private Queries q;
	private List<SongEntry> songList;
	private List<SongEntry> dbSongList;
	private Synchronize synch;
	
	private String url = "http://pseudocodeteam.com/bcg/tapthelyrics/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.song_list);
		
		dbHelper = new DbHelper(this);
		q = new Queries(db, dbHelper);

		dbSongList = q.getAllSongs();
		
		synch = new Synchronize(this, q);
		synch.setOnSynchronizeListener(this);
		synch.beginGettingData();
		
		
//		songList = Data.getSongs();
//		
//		// initialize list adapter as a container for showing list items
//		ListAdapter adapter = new ListAdapter(this, songList.size(), R.layout.high_score_entry);
//		adapter.setOnListAdapterListener(new OnListAdapterListener() 
//		{
//			@Override
//			public View onListAdapterCreated(ListAdapter adapter, View v, int position,
//					ViewGroup viewGroup) 
//			{
//				// TODO Auto-generated method stub
//				SongEntry entry = songList.get(position);
//				Score score = q.getScoreBySongId(entry.id);
//				
//				// set and show the data
//				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
//				tvTitle.setText(entry.title);
//				
//				TextView tvArtist = (TextView) v.findViewById(R.id.tvArtist);
//				tvArtist.setText(entry.artist);
//				
//				TextView tvScore = (TextView) v.findViewById(R.id.tvScore);
//				TextView tvName = (TextView) v.findViewById(R.id.tvName);
//				
//				String scoreStr = (String) ((score == null) ? "None" : String.valueOf(score.score) );
//				
//				String nameStr = (String) ((score == null) ? "None" : String.valueOf(score.name) );
//						
//				tvScore.setText("High Score: " + scoreStr);
//				tvName.setText("Name: " + nameStr);
//				
//				return v;
//			}
//		});
//		
//		// initialize list view and set its adapter
//		ListView listView = (ListView) findViewById(R.id.listView);
//		listView.setOnItemClickListener(this);
//		listView.setAdapter(adapter);
//		listView.setDivider(null);
//		adapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int pos, long resid) 
	{
		
	}

	@Override
	public void onSynchronizeDone(Synchronize synch, ArrayList<SongEntry> list) {
		// TODO Auto-generated method stub
		if(list == null)
		{
			Toast.makeText(getApplicationContext(), 
					"Something went wrong with the server", Toast.LENGTH_SHORT).show();
			
			return;
		}
		
		songList = list;
		
		ListAdapter adapter = new ListAdapter(this, list.size(), R.layout.server_list_entry);
		adapter.setOnListAdapterListener(this);
		
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		listView.setDivider(null);
		adapter.notifyDataSetChanged();
	}

	@Override
	public View onListAdapterCreated(ListAdapter adapter, View v, int position,
			ViewGroup viewGroup) {
		
		// TODO Auto-generated method stub
		final SongEntry entry = songList.get(position);
		
		// set and show the data
		TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		tvTitle.setText(entry.title);
		
		TextView tvArtist = (TextView) v.findViewById(R.id.tvArtist);
		tvArtist.setText(entry.artist);
	
		Boolean isFound = false;
		
		for(SongEntry entryDB : dbSongList)
		{
			if(entry.id == entryDB.id)
			{
				isFound = true;
				break;
			}
		}
		
		ImageView imgStatus = (ImageView) v.findViewById(R.id.imgStatus);
		
		if(!isFound)
		{
			imgStatus.setImageResource(R.drawable.download32);
			imgStatus.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v1) {
					// TODO Auto-generated method stub
					
					try
					{
						synch.downloadFile(url, entry.lyricsFile, entry.songFile);
						q.insertSong(entry);
					}
					catch(Exception e)
					{
						Toast.makeText(getApplicationContext(), 
								"Something went wrong with the network.", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
		}
		else
		{
			imgStatus.setImageResource(R.drawable.exist32);
		}
		
		return v;
	}

	@Override
	public void onDownloadDone(Synchronize synch) {
		// TODO Auto-generated method stub
		dbSongList = q.getAllSongs();
		
		ListAdapter adapter = new ListAdapter(this, songList.size(), R.layout.server_list_entry);
		adapter.setOnListAdapterListener(this);
		
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		listView.setDivider(null);
		adapter.notifyDataSetChanged();
	}


}
