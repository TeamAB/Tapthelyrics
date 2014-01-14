package com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapter extends BaseAdapter 
{	
	private Context c;
	public int noOfItems;
	private int resid;
	public int tag = 0;
	
	OnListAdapterListener mCallback;
	
	
	// callback for list adapter listener
	public interface OnListAdapterListener 
    {
        public View onListAdapterCreated(ListAdapter 
        		adapter, View v, int position, ViewGroup viewGroup);
    }
	
	
	// set the list adapter listener
	public void setOnListAdapterListener(OnListAdapterListener listener)
	{
		try 
		{
            mCallback = (OnListAdapterListener) listener;
        } catch (ClassCastException e) 
        {
            throw new ClassCastException(this.toString() + " must implement OnListAdapterListener");
        }
	}
	
	// constructor
	public ListAdapter(Context c, int noOfItems, int resid)
	{
		this.c = c;
		this.noOfItems = noOfItems;
		this.resid = resid;
	}

	@Override
	public int getCount() 
	{
		// get no of items
		return noOfItems;
	}

	@Override
	public Object getItem(int pos) 
	{
		// get item at a certain index
		return noOfItems;
	}

	@Override
	public long getItemId(int pos) 
	{
		// get item id at a certain index
		return pos;
	}

	@Override
	public View getView(int pos, View v, ViewGroup viewGroup) 
	{
		// create view and return
		
		if(v == null)
		{
			// check if the view is not equal to null, if null, then inflate ingredients_row
			LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(resid, null);
		}
		
		// fire callback
		return mCallback.onListAdapterCreated(this, v, pos, viewGroup);
	}
	
}

