package com.project.tapthelyrics;

import java.util.List;

import com.adapters.ListAdapter;
import com.adapters.ListAdapter.OnListAdapterListener;
import com.db.DbHelper;
import com.db.Queries;
import com.db.Score;
import com.models.SongEntry;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;

public class HighScoreActivity extends Activity implements OnItemClickListener
{
	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private Queries q;
	private List<SongEntry> songList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.song_list);
		
		dbHelper = new DbHelper(this);
		q = new Queries(db, dbHelper);
		
//		songList = Data.getSongs();
		songList = q.getAllSongs();
		
		// initialize list adapter as a container for showing list items
		ListAdapter adapter = new ListAdapter(this, songList.size(), R.layout.high_score_entry);
		adapter.setOnListAdapterListener(new OnListAdapterListener() 
		{
			@Override
			public View onListAdapterCreated(ListAdapter adapter, View v, int position,
					ViewGroup viewGroup) 
			{
				// TODO Auto-generated method stub
				SongEntry entry = songList.get(position);
				Score score = q.getScoreBySongId(entry.id);
				
				// set and show the data
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				tvTitle.setText(entry.title);
				
				TextView tvArtist = (TextView) v.findViewById(R.id.tvArtist);
				tvArtist.setText(entry.artist);
				
				TextView tvScore = (TextView) v.findViewById(R.id.tvScore);
				TextView tvName = (TextView) v.findViewById(R.id.tvName);
				
				String scoreStr = (String) ((score == null) ? "None" : String.valueOf(score.score) );
				
				String nameStr = (String) ((score == null) ? "None" : String.valueOf(score.name) );
						
				tvScore.setText("High Score: " + scoreStr);
				tvName.setText("Name: " + nameStr);
				
				return v;
			}
		});
		
		// initialize list view and set its adapter
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		listView.setDivider(null);
		adapter.notifyDataSetChanged();
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


}
