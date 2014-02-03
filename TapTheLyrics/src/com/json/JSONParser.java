package com.json;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.models.SongEntry;



public class JSONParser 
{
	public String getJSONFromUrl(String url) 
	{	 
		try 
		{
			HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
           
//            httpPost.setEntity(new UrlEncodedFormEntity(params));
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
			String tempResponse = EntityUtils.toString(httpResponse.getEntity());
			
			return tempResponse;
 
        } 
		catch (UnsupportedEncodingException e) 
        {
            e.printStackTrace();
        } 
		catch (ClientProtocolException e) 
        {
            e.printStackTrace();
        } 
		catch (IOException e) 
		{
            e.printStackTrace();
        }
		
		return "";
    }
	
	public ArrayList<SongEntry> obtainCategoryJSONFromURL(String url) throws JSONException
	{
		String strObj = getJSONFromUrl(url);
		
		ArrayList<SongEntry> list = new ArrayList<SongEntry>();
		
		JSONArray jsonArray = new JSONArray(strObj);
		for(int x = 0; x < jsonArray.length(); x++)
		{
			JSONObject jsonObject = jsonArray.getJSONObject(x);
			SongEntry entry = new SongEntry();
			
			entry.id = jsonObject.getInt("id");
			entry.title = jsonObject.getString("title");
			entry.artist = jsonObject.getString("artist");
			entry.lyricsFile = jsonObject.getString("lyricsFile");
			entry.songFile = jsonObject.getString("songFile");
			entry.difficulty = jsonObject.getString("difficulty");
			
			list.add(entry);
		}
		
		return list;
	}
	
	
}
