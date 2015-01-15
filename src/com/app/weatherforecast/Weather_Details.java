package com.app.weatherforecast;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Weather_Details extends Activity {

	TextView date_txt,day_t_txt,min_t_txt,max_t_txt,night_t_txt,eve_t_txt,morn_t_txt,pressure_txt,humidity_txt,speed_txt,deg_txt,desc_txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather__details);
		init();
	}

	private void init() {

		WeatherClass w=getIntent().getParcelableExtra("OBJ");
		
		date_txt=(TextView) findViewById(R.id.date_txt);
		day_t_txt=(TextView)findViewById(R.id.day_t);
		min_t_txt=(TextView)findViewById(R.id.min_t);
		max_t_txt=(TextView)findViewById(R.id.max_t);
		night_t_txt=(TextView)findViewById(R.id.night_t);
		eve_t_txt=(TextView)findViewById(R.id.eve_t);
		morn_t_txt=(TextView)findViewById(R.id.morn_t);
		pressure_txt=(TextView)findViewById(R.id.pressure);
		humidity_txt=(TextView)findViewById(R.id.humidity);
		speed_txt=(TextView)findViewById(R.id.speed);
		desc_txt=(TextView)findViewById(R.id.desc_txt);
		deg_txt=(TextView)findViewById(R.id.deg_txt);
		if(w!=null)
		{
			date_txt.setText(""+w.getDate());
			day_t_txt.setText("Temperature of Day is--"+w.getDay_temp());
			min_t_txt.setText("Minimum Temperature of Day is--"+w.getMin_temp());
			max_t_txt.setText("Maxmum Temperature of Day is--"+w.getMax_temp());
			night_t_txt.setText("Temperature of Night is--"+w.getNight_temp());
			eve_t_txt.setText("Temperature of Evening is--"+w.getEvening_temp());
			morn_t_txt.setText("Temperature of Morning is--"+w.getMorning_temp());
			pressure_txt.setText("Pressure is--"+w.getPressure());
			humidity_txt.setText("Humidity is--"+w.getHumidity());
			speed_txt.setText("Speed is--"+w.getSpeed());
			desc_txt.setText("Description is--"+w.getDesc());
			deg_txt.setText("Deg is--"+w.getDeg());
		}else
		{
			Toast.makeText(getApplicationContext(), "Something Wrong.", Toast.LENGTH_LONG).show();
		}

	}

}
