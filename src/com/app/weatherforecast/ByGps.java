package com.app.weatherforecast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ByGps extends Activity {

	ConnectionDetector cd;
	Boolean isInternetPresent;
	ProgressDialog pd = null;
	private TextView cityncountryname_txt;
	String country,city,url;
	ArrayList<WeatherClass> date_array=new ArrayList<WeatherClass>();
	ArrayList<WeatherClass> w_array=new ArrayList<WeatherClass>();
	private ListView date_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_by_gps);

		init();
	}

	private void init() {
		date_list=(ListView)findViewById(R.id.date_list);
		date_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Log.e("app", ""+w_array.get(position).toString());
					if(position%15==0)
					{
						Log.e("app", ""+position+"----"+w_array.get(position).toString());
					}else
					{
						WeatherClass w=w_array.get(position);
						Intent i =new Intent(ByGps.this,Weather_Details.class);
						i.putExtra("OBJ", w);
						startActivity(i);
					}
			}
		});
		GPSTracker gpsTracker = new GPSTracker(this);
		cityncountryname_txt=(TextView)findViewById(R.id.cityncountryname_gps);
		if (gpsTracker.canGetLocation())
		{
			Log.e("app", ""+gpsTracker.latitude+"..."+gpsTracker.longitude);
			cd=new ConnectionDetector(ByGps.this);
			isInternetPresent=cd.isConnectingToInternet();
			if(isInternetPresent)
			{
				Log.e("app", "...city name is.."+gpsTracker.getPostalCode(ByGps.this));
				if(gpsTracker.getPostalCode(ByGps.this)!=null)
				{
					url="http://api.openweathermap.org/data/2.5/forecast/daily?q="+gpsTracker.getPostalCode(ByGps.this)+"&cnt=14&APPID=2296c2ab591cb588ab3c472d2e92af51";
				}
				else
				{
					url="http://api.openweathermap.org/data/2.5/forecast/daily?lat="+gpsTracker.latitude+"&lon="+gpsTracker.longitude+"&cnt=14&APPID=2296c2ab591cb588ab3c472d2e92af51";
				}
			
				new  WebConnectionAsyncgetWetherDeatils(url,ByGps.this).execute();
			}else
			{
				Toast.makeText(getApplicationContext(), "Please check internet connection.", Toast.LENGTH_LONG).show();
			}
		}else
		{
			Toast.makeText(getApplicationContext(), "Please check internet connection.", Toast.LENGTH_LONG).show();
		}
	}

	public class WebConnectionAsyncgetWetherDeatils extends AsyncTask<String, Object, Object> {

		String val = null;

		Context context = null;

		String url = null;

		public WebConnectionAsyncgetWetherDeatils(String url,Context ctx ){
			this.url = url;

			context = ctx;

		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = ProgressDialog.show(ByGps.this, "", "Please Wait...");
		}
		@Override
		protected Object doInBackground(String... params) {		


			try{
				val = HttpConnection.postData(url);
				Log.e("response", "we"+val);
				getWetherforcast(val);
			}catch (Exception e) {			
				e.printStackTrace();


			}
			return val;		
		}




		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			pd.dismiss();
			if(city!=null && country!=null)
			{
				cityncountryname_txt.setText(country+" "+city);
				if(w_array.size()!=0)
				{
					MyAdpater adpater=new MyAdpater(ByGps.this, R.layout.list_row_ite, w_array);
					date_list.setAdapter(adpater);

				}
			}

		}

	}
	private void getWetherforcast(String val2) {
		try {

			JSONObject jsonObj = new JSONObject(val2);
			final String cod=jsonObj.getString("cod");
			ByGps.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if(cod.equals("404"))
					{
						Toast.makeText(getApplicationContext(), "Oops Something Wrong.", Toast.LENGTH_LONG).show();
						finish();
					}

				}
			});
			JSONObject weather_result = jsonObj.getJSONObject("city");	
			country=weather_result.getString("country");
			city=weather_result.getString("name");
		
			WeatherClass wc1=new WeatherClass();
			wc1.setDate(city);
			w_array.add(wc1);
			JSONArray weather_array=jsonObj.getJSONArray("list");

			for(int index=0;index<weather_array.length();index++)
			{
				WeatherClass wc=new WeatherClass();
				JSONObject weather_list = weather_array.getJSONObject(index);
				JSONArray warrays=weather_list.getJSONArray("weather");

				for(int i=0;i<warrays.length();i++)
				{
					JSONObject w_obj=warrays.getJSONObject(i);

					wc.setDesc(w_obj.getString("description"));
				}

				long unixSeconds =Long.parseLong(weather_list.getString("dt"));
				Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
				SimpleDateFormat sdf = new SimpleDateFormat("dd MMM,yyyy"); // the format of your date
				sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
				String formattedDate = sdf.format(date);
			

				wc.setDate(formattedDate);
				Log.e("app", "Pressure name is...*********************"+formattedDate+"***************************");	
				JSONArray w=weather_list.getJSONArray("weather");

				wc.setPressure(weather_list.getString("pressure"));

				wc.setHumidity(weather_list.getString("humidity"));

				wc.setSpeed(weather_list.getString("speed"));
				wc.setDeg(weather_list.getString("deg"));

				JSONObject weather= weather_list.getJSONObject("temp");

				wc.setDay_temp(weather.getString("day"));

				wc.setMin_temp(weather.getString("min"));

				wc.setMax_temp(weather.getString("max"));

				wc.setNight_temp(weather.getString("night"));

				wc.setEvening_temp(weather.getString("eve"));

				wc.setMorning_temp(weather.getString("morn"));

				w_array.add(wc);


			}



		} catch (Exception e) {
			Log.e("exce", ""+e.toString());
		}

	}
}
