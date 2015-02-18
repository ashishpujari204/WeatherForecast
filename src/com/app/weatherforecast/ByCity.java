package com.app.weatherforecast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ByCity extends Activity {

	private EditText cityname_txt;
	ConnectionDetector cd;
	Boolean isInternetPresent;
	String country,city;
	private TextView cityncountryname_txt;
	
	private RelativeLayout cityname_layout;
	private ListView date_list_city;
	ArrayList<WeatherClass> date_array=new ArrayList<WeatherClass>();
	ArrayList<WeatherClass> w_array=new ArrayList<WeatherClass>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_by_city);

		init();//intialize methods ******


	}

	private void init() {

		cityname_txt=(EditText)findViewById(R.id.cityname);
		cityname_layout=(RelativeLayout)findViewById(R.id.cityname_layout);
		date_list_city=(ListView)findViewById(R.id.date_list_city);
		cityncountryname_txt=(TextView)findViewById(R.id.cityncountryname_city);
		date_list_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e("app", ""+w_array.get(position).toString());

				if(position%15==0)
				{
					
				}else
				{
					WeatherClass w=w_array.get(position);
					Intent i =new Intent(ByCity.this,Weather_Details.class);
					i.putExtra("OBJ", w);
					startActivity(i);
				}

			}
		});
		findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(cityname_txt.getText().toString().trim().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Oops you missing city name.", Toast.LENGTH_LONG).show();
				}else
				{
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					GPSTracker gpsTracker = new GPSTracker(ByCity.this);
					if (gpsTracker.canGetLocation())
					{
						cd=new ConnectionDetector(ByCity.this);
						isInternetPresent=cd.isConnectingToInternet();
						if(isInternetPresent)
						{
							try
							{
								String str=cityname_txt.getText().toString();
								List<String> city_name=Arrays.asList(str.split(","));
								for(int i=0;i<city_name.size();i++)
								{
									String url="http://api.openweathermap.org/data/2.5/forecast/daily?q="+city_name.get(i).toString().trim()+"&cnt=14&APPID=2296c2ab591cb588ab3c472d2e92af51";

									new  WebConnectionAsyncgetWetherDeatils(url,ByCity.this).execute();
								}
								cityname_layout.setVisibility(View.GONE);
								date_list_city.setVisibility(View.VISIBLE);
							}
							catch(Exception e)
							{
								Toast.makeText(getApplicationContext(), "oops somethign wrong.", Toast.LENGTH_LONG).show();
								Log.e("app", "Exce---"+e.toString());
							}
						}else
						{
							Toast.makeText(getApplicationContext(), "Please check internet connection.", Toast.LENGTH_LONG).show();
						}


					}else
					{
						Toast.makeText(getApplicationContext(), "Please check internet connection.", Toast.LENGTH_LONG).show();
					}
				}

			}
		});
	}
	public class WebConnectionAsyncgetWetherDeatils extends AsyncTask<String, Object, Object> {

		String val = null;
		ProgressDialog pd = null;
		Context context = null;

		String url = null;

		public WebConnectionAsyncgetWetherDeatils(String url,Context ctx ){
			this.url = url;

			context = ctx;

		}
		@Override
		protected void onPreExecute() {
		
			super.onPreExecute();
			pd = ProgressDialog.show(ByCity.this, "", "Please Wait...");
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
				cityncountryname_txt.setVisibility(View.GONE);
				if(w_array.size()!=0)
				{
					MyAdpater adpater=new MyAdpater(ByCity.this, R.layout.list_row_ite, w_array);
					date_list_city.setAdapter(adpater);

				}
			}
		}

	}
	private void getWetherforcast(String val2) {
		try {

			JSONObject jsonObj = new JSONObject(val2);
			final String cod=jsonObj.getString("cod");
			ByCity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if(cod.equals("404"))
					{
						Toast.makeText(getApplicationContext(), "Oops Something Wrong.", Toast.LENGTH_LONG).show();
						
					}

				}
			});

			JSONObject weather_result = jsonObj.getJSONObject("city");	
			country=weather_result.getString("country");
			city=weather_result.getString("name");
			Log.e("app", "City name is..."+weather_result.getString("name"));
			Log.e("app", "City id is..."+weather_result.getString("id"));
			Log.e("app", "Country  is..."+weather_result.getString("country"));
			WeatherClass wc1=new WeatherClass();
			wc1.setDate(city+"  "+country);
			w_array.add(wc1);
			

			JSONArray weather_array=jsonObj.getJSONArray("list");

			for(int index=0;index<weather_array.length();index++)
			{
				WeatherClass wc=new WeatherClass();
				wc.setCityname(city);

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
			
			ByCity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					
						Toast.makeText(getApplicationContext(), "Oops Something Wrong.", Toast.LENGTH_LONG).show();
					

				}
			});

			Log.e("exce", ""+e.toString());
		}

	}

}
