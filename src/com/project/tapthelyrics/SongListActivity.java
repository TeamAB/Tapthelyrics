package com.project.tapthelyrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.adapters.ListAdapter;
import com.adapters.ListAdapter.OnListAdapterListener;
import com.db.DbHelper;
import com.db.Queries;
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
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;

public class SongListActivity extends Activity implements OnItemClickListener
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
		
		String difficulty = this.getIntent().getStringExtra("difficulty");
		
		dbHelper = new DbHelper(this);
		q = new Queries(db, dbHelper);

		songList = q.getAllSongs();
		
		List<SongEntry> filter = new ArrayList<SongEntry>();
		
		for(SongEntry entry : songList)
		{
			if(entry.difficulty.compareTo(difficulty) == 0)
				filter.add(entry);
		}
		
		songList = filter;
		
		
		// Adapter to show the list items
		ListAdapter adapter = new ListAdapter(this, songList.size(), R.layout.list_entry);
		adapter.setOnListAdapterListener(new OnListAdapterListener() 
		{
			@Override
			public View onListAdapterCreated(ListAdapter adapter, View v, int position,
					ViewGroup viewGroup) 
			{
				// TODO Auto-generated method stub
				SongEntry entry = songList.get(position);
				
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				tvTitle.setText(entry.title);
				
				TextView tvArtist = (TextView) v.findViewById(R.id.tvArtist);
				tvArtist.setText(entry.artist);
				
				return v;
			}
		});
		
		// initialize list view and set the adapter
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
		// get the SongEntry at a certain position and pass it to other classes
		SongEntry entry = songList.get(pos);
		
		Intent i = new Intent(this, PlayActivity3.class);
		i.putExtra("entry", (Serializable) entry);
		startActivity(i);
	}


}
