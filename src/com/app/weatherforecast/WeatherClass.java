package com.app.weatherforecast;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherClass implements Parcelable{

	String date,day_temp,min_temp,max_temp,night_temp,evening_temp,morning_temp,pressure,humidity,speed,deg,desc,cityname;

	public WeatherClass() {
		super();
	}

	public WeatherClass(String date, String day_temp, String min_temp,
			String max_temp, String night_temp, String evening_temp,
			String morning_temp, String pressure, String humidity,
			String speed, String deg, String desc,String cityname) {
		super();
		this.date = date;
		this.day_temp = day_temp;
		this.min_temp = min_temp;
		this.max_temp = max_temp;
		this.night_temp = night_temp;
		this.evening_temp = evening_temp;
		this.morning_temp = morning_temp;
		this.pressure = pressure;
		this.humidity = humidity;
		this.speed = speed;
		this.deg = deg;
		this.desc = desc;
		this.cityname=cityname;
		
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDay_temp() {
		return day_temp;
	}

	public void setDay_temp(String day_temp) {
		this.day_temp = day_temp;
	}

	public String getMin_temp() {
		return min_temp;
	}

	public void setMin_temp(String min_temp) {
		this.min_temp = min_temp;
	}

	public String getMax_temp() {
		return max_temp;
	}

	public void setMax_temp(String max_temp) {
		this.max_temp = max_temp;
	}

	public String getNight_temp() {
		return night_temp;
	}

	public void setNight_temp(String night_temp) {
		this.night_temp = night_temp;
	}

	public String getEvening_temp() {
		return evening_temp;
	}

	public void setEvening_temp(String evening_temp) {
		this.evening_temp = evening_temp;
	}

	public String getMorning_temp() {
		return morning_temp;
	}

	public void setMorning_temp(String morning_temp) {
		this.morning_temp = morning_temp;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDeg() {
		return deg;
	}

	public void setDeg(String deg) {
		this.deg = deg;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "WeatherClass [date=" + date + ", day_temp=" + day_temp
				+ ", min_temp=" + min_temp + ", max_temp=" + max_temp
				+ ", night_temp=" + night_temp + ", evening_temp="
				+ evening_temp + ", morning_temp=" + morning_temp
				+ ", pressure=" + pressure + ", humidity=" + humidity
				+ ", speed=" + speed + ", deg=" + deg + ", desc=" + desc + "City.."+cityname+"]";
	}
	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.date);
		dest.writeString(this.day_temp);
		dest.writeString(this.min_temp);
		dest.writeString(this.max_temp);
		dest.writeString(this.night_temp);
		dest.writeString(this.evening_temp);
		dest.writeString(this.morning_temp);
		dest.writeString(this.pressure);
		dest.writeString(this.humidity);
		dest.writeString(this.speed);
		dest.writeString(this.deg);
		dest.writeString(this.desc);
		dest.writeString(this.cityname);
		
	}
	
public static Parcelable.Creator<WeatherClass> CREATOR=new Creator<WeatherClass>() {
		
		@Override
		public WeatherClass[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public WeatherClass createFromParcel(Parcel source) {
			WeatherClass d =new WeatherClass();
			d.date=source.readString();
			d.day_temp=source.readString();
			d.min_temp=source.readString();
			d.max_temp=source.readString();
			d.night_temp=source.readString();
			d.evening_temp=source.readString();
			d.morning_temp=source.readString();
			d.pressure=source.readString();
			d.humidity=source.readString();
			d.speed=source.readString();
			d.deg=source.readString();
			d.desc=source.readString();
			d.cityname=source.readString();
			return d;
		}
	};
}
