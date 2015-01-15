package com.app.weatherforecast;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdpater extends ArrayAdapter<WeatherClass> {

	Context context;
	ArrayList<WeatherClass> array;
	public MyAdpater(Context context, int resourceId,
			ArrayList<WeatherClass> items) {
		super(context, resourceId, items);
		this.context = context;
		this.array=items;
		Log.e("app", "..."+array.toString());
	}


	private class ViewHolder {

		TextView txt;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_row_ite, null);
			holder = new ViewHolder();
			holder.txt = (TextView) convertView.findViewById(R.id.txt);
			
			convertView.setTag(holder);
			convertView.setTag(R.id.txt,	holder.txt);
			
			
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.txt.setTag(position); 
	  
		
		holder.txt.setText(array.get(position).getDate());
		
		
		if(array.get(position).toString().contains("2015"))
		{
			Log.e("app", "...postion.."+position);
			holder.txt.setBackgroundColor(context.getResources().getColor(R.color.white));
		}else
		{
			
			holder.txt.setBackgroundColor(context.getResources().getColor(R.color.name));
		}
		

		
		return convertView;
	}


}
