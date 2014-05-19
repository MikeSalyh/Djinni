package app_weatherman;

/* 
 * The YahooWeatherData class contains information about the weather in a specific location.
 * It is built from the Yahoo Weather API, documentation for which can be found here: https://developer.yahoo.com/weather/
 */

import java.io.*;
//import javax.xml.stream.*;	// this is going to be used to parse the XML.
import java.sql.Time;
import java.net.URL;

public class YahooWeatherData {
	
	private static final String URL_BASE = "http://weather.yahooapis.com/forecastrss?w=";
	
	// CONSTRUCTOR. Requires a WOEID for the desired region.
	// For more information, see https://developer.yahoo.com/geo/geoplanet/guide/concepts.html
	// To lookup the WOEID of a place, use http://woeid.rosselliot.co.nz/
	public YahooWeatherData( int WOEID) throws IOException
	{
		URL apiRequest = new URL( URL_BASE + WOEID);
		
		// PROXY. Prints the weather document to console.
		BufferedReader reader = new BufferedReader(new InputStreamReader(apiRequest.openStream()));
		String currentLine;
		while (( currentLine = reader.readLine() ) != null) {
			System.out.println(currentLine);
		}
		reader.close();
		// END OF PROXY.
	}	
}


/* The location of today's weather. All parameters taken from Yahoo XML */
class Location{
	public String city, region, country;
	public float latitude, longitude;
	public Location( String city, String region, String country, float latitude, float longitude)
	{
		this.city = city;
		this.region = region;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}

/* What unit the measures are in (F or C, Mi or Km, etc). 
 * All parameters taken from Yahoo XML */
class Units{
	public String temperature, distance, pressure, speed;
	public Units( String temperature, String distance, String pressure, String speed)
	{
		this.temperature = temperature;
		this.distance = distance;
		this.pressure = pressure;
		this.speed = speed;
	}
}


/* Today's weather forecast. All parameters taken from Yahoo XML */
class Weather{
	public String condition, date;
	public int code, temp;
	public Weather( String condition, int code, int temp, String date)
	{
		this.condition = condition;
		this.code = code;
		this.temp = temp;
		this.date = date;
	}
}

/* Today's astronomy data. All parameters taken from Yahoo XML */
class Astronomy{
	public Time sunrise, sunset;
	public Astronomy( Time sunrise, Time sunset)
	{
		this.sunrise = sunrise;
		this.sunset = sunset;
	}
}

/* Today's atmosphere conditions. All parameters taken from Yahoo XML */
class Atmosphere{
	public int humidity, visibility;
	public float pressure;
	public int rising; // state of the barometric pressure: steady (0), rising (1), or falling (2). 
	public Atmosphere( int humidity, int visibility, float pressure, int rising)
	{
		this.humidity = humidity;
		this.visibility = visibility;
		this.pressure = pressure;
		this.rising = rising;
	}
}

/* Today's wind data. All parameters taken from Yahoo XML */
class Wind{
	public int chill, direction, speed;
	public Wind( int chill, int direction, int speed)
	{
		this.chill = chill;
		this.direction = direction;
		this.speed = speed;
	}
}

/* The 5-day forecast. Made up of 5 ForecastDay objects, arranged in an array. */
class Forecast{
	public ForecastDay[] forecastArray = new ForecastDay[5];
	public Forecast( ForecastDay today, ForecastDay day1, ForecastDay day2, ForecastDay day3, ForecastDay day4)
	{
		forecastArray[0] = today;
		forecastArray[1] = day1;
		forecastArray[2] = day2;
		forecastArray[3] = day3;
		forecastArray[4] = day4;
	}
}

/* The forecast of a day. Used for the 5-day forecast. All parameters taken from Yahoo XML */
class ForecastDay{
	public String day, condition;
	public int high, low;
	public ForecastDay( String day, String condition, int high, int low)
	{
		this.day = day;
		this.condition = condition;
		this.high = high;
		this.low = low;
	}
}




