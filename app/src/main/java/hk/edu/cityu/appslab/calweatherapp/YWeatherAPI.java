package hk.edu.cityu.appslab.calweatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class YWeatherAPI {
	
	// Yahoo Weather API address
	public static String BASE_URL = "http://weather.yahooapis.com/forecastrss?u=c&w=";
	public static String WOEID = Constant.HONG_KONG_WOEID;
//	public final static String URL = "http://weather.yahooapis.com/forecastrss?u=c&w=2165352";
	
	public static String getForecastXml(){
		Log.i("test", "wid = "+WOEID);
		return httpGet(BASE_URL+WOEID);
	}
	public static void setWID(String woeid){
		WOEID = woeid;
	}
	private static String httpGet(String url){
		
		String result;
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

			if (httpEntity != null) {
				InputStream inputStream = httpEntity.getContent();
				Reader in = new InputStreamReader(inputStream);
				BufferedReader bufferedreader = new BufferedReader(in);
				StringBuilder stringBuilder = new StringBuilder();

				String stringReadLine = null;

				while ((stringReadLine = bufferedreader.readLine()) != null) {
					stringBuilder.append(stringReadLine + "\n");
				}

				result = stringBuilder.toString();
				
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	

}
