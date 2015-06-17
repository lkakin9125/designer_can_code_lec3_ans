package hk.edu.cityu.appslab.calweatherapp;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class WeatherParser {

	private class TAG {
		final static String FORECAST = "yweather:forecast";
		final static String DAY = "day";
		final static String DATE = "date";
		final static String LOW = "low";
		final static String HIGH = "high";
		final static String TEXT = "text";
		final static String CODE = "code";
	}

	private String xml;
	private XmlPullParserFactory factory;
	private XmlPullParser xpp;

	public WeatherParser(String xml) throws XmlPullParserException, IOException {
		this.xml = xml;

		factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		xpp = factory.newPullParser();

		xpp.setInput(new StringReader(this.xml));

	}

	public List<Weather> getWeatherForecastList() {
		ArrayList<Weather> weatherList = new ArrayList<Weather>();

		int eventType;
		try {
			eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					if (xpp.getName().equalsIgnoreCase(TAG.FORECAST)) {
						Weather weather = new Weather();
						weather.setDay(xpp.getAttributeValue(null, TAG.DAY));
						weather.setDate(xpp.getAttributeValue(null, TAG.DATE));
						weather.setLow(Integer.parseInt(xpp.getAttributeValue(
								null, TAG.LOW)));
						weather.setHigh(Integer.parseInt(xpp.getAttributeValue(
								null, TAG.HIGH)));
						weather.setText(xpp.getAttributeValue(null, TAG.TEXT));
						weather.setIcon(weatherIconSwitch(Integer.parseInt(xpp
								.getAttributeValue(null, TAG.CODE))));

						// Add weather object to the weather list
						weatherList.add(weather);

					}
				}
				eventType = xpp.next();

			}
			return weatherList;

		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private int weatherIconSwitch(int weather) {
		switch (weather) {
		case 3:
			return R.drawable.thunderstorm;
		case 4:
			return R.drawable.thunderstorm;
		case 5:
			return R.drawable.snowy;
		case 6:
			return R.drawable.snowy;
		case 7:
			return R.drawable.snowy;
		case 8:
			return R.drawable.partly_snowy;
		case 9:
			return R.drawable.rainny;
		case 10:
			return R.drawable.snowy;
		case 11:
			return R.drawable.rainny;
		case 12:
			return R.drawable.rainny;
		case 13:
			return R.drawable.snowy;
		case 14:
			return R.drawable.partly_snowy;
		case 15:
			return R.drawable.snowy;
		case 16:
			return R.drawable.snowy;
		case 18:
			return R.drawable.partly_snowy;
		case 19:
			return R.drawable.partly_snowy;
		case 26:
			return R.drawable.cloudy;
		case 27:
			return R.drawable.partly_cloudy;
		case 28:
			return R.drawable.partly_cloudy;
		case 29:
			return R.drawable.partly_sunny;
		case 30:
			return R.drawable.partly_sunny;
		case 31:
			return R.drawable.sunny;
		case 32:
			return R.drawable.sunny;
		case 33:
			return R.drawable.sunny;
		case 34:
			return R.drawable.sunny;
		case 35:
			return R.drawable.heavy_rainny;
		case 36:
			return R.drawable.sunny;
		case 37:
			return R.drawable.thunderstorm;
		case 38:
			return R.drawable.thunderstorm;
		case 39:
			return R.drawable.thunderstorm;
		case 40:
			return R.drawable.rainny;
		case 41:
			return R.drawable.snowy;
		case 42:
			return R.drawable.partly_snowy;
		case 43:
			return R.drawable.snowy;
		case 44:
			return R.drawable.partly_sunny;
		case 45:
			return R.drawable.thunderstorm;
		case 46:
			return R.drawable.partly_snowy;
		case 47:
			return R.drawable.thunderstorm;
		default:
			return R.drawable.sunny;
		}
	}
}