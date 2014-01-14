package com.json;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONException;
import com.db.Queries;
import com.models.SongEntry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class Synchronize 
{
	
	private static String SONG_URL = "";
	private static String SD_CARD_DIR = "/uploads";
	
	private Activity activity;
	public Queries q;
	
	public Synchronize(Activity activity, Queries q)
	{
		this.activity = activity;
		this.q = q;
	}
	

	public void beginGettingData()
	{
		new SendPostReqAsyncTask().execute();
	}
	
	
	public class SendPostReqAsyncTask extends AsyncTask<Void, Integer, Void>
	{
		ProgressDialog dialog;
		ArrayList<SongEntry> categoryList;
		
		@Override
		protected void onPostExecute(Void result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			dialog.dismiss();
			
			if(mCallback != null)
				mCallback.onSynchronizeDone(Synchronize.this, categoryList);
		}

		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog = ProgressDialog.show(activity, "", "Please wait... ", true);
			dialog.setTitle("Fetching Data");
			dialog.setIcon(android.R.drawable.ic_dialog_info);
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) 
		{
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			dialog.setMessage("Downloading... ("+ values[1] +"/"+ values[0] +")");
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			// TODO Auto-generated method stub
			JSONParser parser = new JSONParser();
			try {
				
//				q.dropAllTableAndCreate();
				
				categoryList = parser.obtainCategoryJSONFromURL(SONG_URL);
				
				
				
//				for(SongEntry category : categoryList)
//				{
//					q.insertSong(category);
//				}
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return null;
		}

	}

	OnSynchronizeListener mCallback;

	// callback for list adapter listener
	public interface OnSynchronizeListener 
    {
        public void onSynchronizeDone(Synchronize 
        		synch, ArrayList<SongEntry> list);
        
        public void onDownloadDone(Synchronize synch);
    }
	
	
	// set the list adapter listener
	public void setOnSynchronizeListener(OnSynchronizeListener listener)
	{
		try 
		{
            mCallback = (OnSynchronizeListener) listener;
        } catch (ClassCastException e) 
        {
            throw new ClassCastException(this.toString() + " must implement OnSynchronizeListener");
        }
	}
	
	
	
//	public void downloadFile(final String url, final String lyricsFile, final String songFile)
//	{
//		
//		final ProgressDialog dialog = ProgressDialog.show(activity, "Dowloading File", "Please Wait", true);
//		Thread t = new Thread(new Runnable() 
//		{	
//			@Override
//			public void run() 
//			{
//				// TODO Auto-generated method stub
//				String []split1 = lyricsFile.split("/");
//				String []split2 = songFile.split("/");
//				
//				downloadFromUrl(dialog, url, split1[1]);
//				downloadFromUrl(dialog, url, split2[1]);
//				dialog.dismiss();
//			}
//		});
//		
//		t.start();
//	}
	
	
	public void downloadFile(final String url, final String lyricsFile, final String songFile)
	{
		DowloadReqAsyncTask task = new DowloadReqAsyncTask(url, lyricsFile, songFile);
		task.execute();
	}
	
	private void downloadFromUrl(final DowloadReqAsyncTask task, String downloadUrl, String fileName, Boolean isSong) 
	{
	   try {
		   File root = android.os.Environment.getExternalStorageDirectory();               

           File dir = new File (root.getAbsolutePath() + "" + SD_CARD_DIR);
           if(dir.exists() == false) {
                dir.mkdirs();
           }

           downloadUrl = downloadUrl.replace(" ", "%20");
           URL url = new URL(downloadUrl); //you can write here any link
           
           File file = new File(dir, fileName);

           long startTime = System.currentTimeMillis();
           
           Log.d("startTime", "" + startTime);
           Log.d("DownloadManager", "download begining");
           Log.d("DownloadManager", "download url:" + url);
           Log.d("DownloadManager", "downloaded file name:" + fileName);

           //create the new connection
           HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

           //set up some things on the connection
           urlConnection.setRequestMethod("GET");

           urlConnection.setDoOutput(true);

           //and connect!
           urlConnection.connect();

           //this will be used to write the downloaded data into the file we created
           FileOutputStream fileOutput = new FileOutputStream(file);

           //this will be used in reading the data from the internet
           InputStream inputStream = urlConnection.getInputStream();

           //this is the total size of the file
           int totalSize = urlConnection.getContentLength();

           //variable to store total downloaded bytes
           int downloadedSize = 0;

           //create a buffer...
           byte[] buffer = new byte[10000];

           int bufferLength = 0; //used to store a temporary size of the buffer

           //now, read through the input buffer and write the contents to the file
           while ( (bufferLength = inputStream.read(buffer)) != -1 ) 
           {
        	   //add the data in the buffer to the file in the file output stream (the file on the sd card
        	   fileOutput.write(buffer, 0, bufferLength);

        	   //add up the size so we know how much is downloaded
        	   downloadedSize += bufferLength;

        	   final int progress = (int)(downloadedSize*100/totalSize);
         
        	   Log.e("SIZE", downloadedSize + "/" + totalSize);

        	   //this is where you would do something to report the prgress, like this maybe
        	   
        	   if(isSong)
        	   {
        		   activity.runOnUiThread(new Runnable() {
	   					@Override
	   					public void run() {
	   						// TODO Auto-generated method stub
	   						 task.dialog.setMessage("Downloading (" + progress + "%)");
	   					}
	   				});
        	   }
        	  

           }
           

           //close the output stream when done
           fileOutput.close();
           
           activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(mCallback != null)
				        	   mCallback.onDownloadDone(Synchronize.this);
					}
				});
           
           

	   } 
	   catch (IOException e) 
	   {
	       Log.d("DownloadManager", "Error: " + e);
	   }

	}
	
	
	public class DowloadReqAsyncTask extends AsyncTask<Void, Integer, Void>
	{
		ProgressDialog dialog;
		ArrayList<SongEntry> categoryList;
		
		String url;
		String lyricsFile;
		String songFile;
		
		String lyricsFileName;
		String songFileName;
		
		public DowloadReqAsyncTask(String url, final String lyricsFile, final String songFile)
		{
			String []split1 = lyricsFile.split("/");
			String []split2 = songFile.split("/");
			
			this.lyricsFile = lyricsFile;
			this.songFile = songFile;
			
			this.url = url;
			this.lyricsFileName = split1[1];
			this.songFileName = split2[1];
		}
		
		@Override
		protected void onPostExecute(Void result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			dialog.dismiss();

		}

		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog = ProgressDialog.show(activity, "Dowloading File", "Please wait... ", true);
			dialog.setIcon(android.R.drawable.ic_dialog_info);
			
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) 
		{
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			dialog.setMessage("Downloading... ("+ values[1] +"/"+ values[0] +")");
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			// TODO Auto-generated method stub
			downloadFromUrl(this, url + lyricsFile, lyricsFileName, false);
			downloadFromUrl(this, url + songFile, songFileName, true);
			
			return null;
		}

	}
}
