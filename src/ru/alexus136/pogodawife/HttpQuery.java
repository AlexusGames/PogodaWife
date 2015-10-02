package ru.alexus136.pogodawife;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import android.util.Log;

public class HttpQuery {

	public static final String TAG = "HttpQuery";
	//public static final String HTTP_ADRESS = "http://xml.meteoservice.ru/export/gismeteo/point/175.xml";
	public static final String HTTP_ADRESS = "http://export.yandex.ru/weather-ng/forecasts/29430.xml";
	private static final String XML_FORECAST = "temperature";
	
	
	byte [] getUrlBytes(String urlSpec) throws IOException {
		URL url = new URL(urlSpec);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = connection.getInputStream();
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			return out.toByteArray();
		} finally {
			connection.disconnect();
		}
	}
	
	public String getUrl(String urlSpec) throws IOException {
		return new String(getUrlBytes(urlSpec));
	}
	
	
	public  ArrayList <Forecast> fetchWeather(int idWidget) {
		ArrayList <Forecast> items = new ArrayList <Forecast>();
		try {
			String xmlString = getUrl(HTTP_ADRESS);
			Log.i(TAG, "Received xml: " + xmlString);
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xmlString));
			
			parseItems(items, parser, idWidget);
		} catch (IOException e) {
			Log.e(TAG, "Failed to fetch items", e);
		} catch (XmlPullParserException e) {
			Log.e(TAG, "Failed to parse items", e);
		}
		return items;
	}
	void parseItems(ArrayList<Forecast> items, XmlPullParser parser, int idWidget)
			throws XmlPullParserException, IOException{
		int eventType = parser.next();
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG && 
				XML_FORECAST.equals(parser.getName())){
				eventType = parser.next();
				if (eventType == XmlPullParser.TEXT) {
					String currentTemperature = parser.getText();
					Forecast item = new Forecast(idWidget);
					item.setCurrentTemperature(currentTemperature);
					/*item.setMaxTemperature(maxTemperature);*/
					
					items.add(item);
					break;
					
				}
				//String minTemperature = parser.getAttributeValue(null, "min");
				//String maxTemperature = parser.getAttributeValue(null, "max");
				
				//String minTemperature = parser.getAttributeValue(null, "color");
				
			}
			eventType = parser.next();
		}
	}
}
