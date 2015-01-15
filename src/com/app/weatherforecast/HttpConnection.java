package com.app.weatherforecast;



import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpConnection {
	public static String flg ;
	public static String f;
	public static String  responseBody;
	public static String prev="";
	public static String postData(String url, List<NameValuePair> arrayList) {
		responseBody = null;
		try {
			HttpResponse response = null;
			HttpClient httpclient = new DefaultHttpClient();
			try
			{
				HttpPost httppost = new HttpPost(url);
				httppost.setEntity(new UrlEncodedFormEntity(arrayList));
				response = httpclient.execute(httppost);
				//Log.d("app", EntityUtils.toString(response.getEntity()));
				responseBody = EntityUtils.toString(response.getEntity());
				Log.d("vote", "rb"+responseBody); 
			}
			catch (Exception e) {
				e.printStackTrace();
			}
 
		} catch (Exception e) {
		}
		return responseBody;  
	}
	public static String getflg()
	{
		flg = f;
		return flg;
	}
	public static String postData(String url) {
		responseBody = null;
		try {
			HttpResponse response = null;
			HttpClient httpclient = new DefaultHttpClient();
			try
			{
				HttpPost httppost = new HttpPost(url);
				
				response = httpclient.execute(httppost);
				//Log.d("app", EntityUtils.toString(response.getEntity()));
				responseBody = EntityUtils.toString(response.getEntity());
				Log.d("vote", "rb"+responseBody); 
			}
			catch (Exception e) {
				e.printStackTrace();
			}
 
		} catch (Exception e) {
		}
		return responseBody;  
	}
}

