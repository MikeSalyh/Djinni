package app_weatherman;

/** 
 * The YahooWeatherData class represents information about the weather in a specific location.
 * It is built from the Yahoo Weather API, documentation for which can be found here: https://developer.yahoo.com/weather/
 * @author mikesalyh
 */

import java.io.*;
import java.sql.Time;
import java.net.MalformedURLException;
import java.net.URL;

public class YahooWeatherData {
	
//	-------------------------------
//		VARIABLES & CONSTANTS 
//	-------------------------------

//	the API request, sans desired city. Append a WOEID to this URL to make a valid request 
	private static final String URL_BASE = "http://weather.yahooapis.com/forecastrss?w=";
	
//	Objects that hold the weather data. 
	private Location 		myLocation;
	private Units 			myUnits; 		
	private Weather 		myWeather; 		
	private Astronomy 		myAstronomy; 	
	private Atmosphere 		myAtmosphere; 	
	private Wind 			myWind;			
	private FiveDayForecast myForecast; 		

	
//	-------------------------------
//			  CONSTRUCTOR
//	-------------------------------
	
	/** 
	 * Constructor. Requires a WOEID for the desired region.
	 * For more information, see https://developer.yahoo.com/geo/geoplanet/guide/concepts.html
	 * To lookup the WOEID of a place, use http://woeid.rosselliot.co.nz/
	 */ 
	public YahooWeatherData( int WOEID) 
	{
		/*
		URL 			apiRequest;		// the URL to the Yahoo Weather API			
		InputStream 	apiStream;		// the stream of data recieved by the Yahoo Weather API.
		BufferedReader	weatherReader;	// reads the XML object that the Yahoo Weather API returns. 
		
		try {
			// First, combine the URL base with the provided WOEID. This forms the API request.
			apiRequest = new URL( URL_BASE + WOEID);
			
			// Next, submit the request to the Yahoo Weather API. 
			try{
				apiStream = apiRequest.openStream();
				
				// Then, create a BufferedReader to read the XML response of the Yahoo Weather API.
				weatherReader = new BufferedReader( new InputStreamReader( apiStream));				
			} 
			catch( IOException e2)
			{
				// If the request is valid, but for some reason the user cannot
				// connect to the Yahoo Weather API, handle the failed connection.
				handleFailedConnection( e2);
			}
			
		} 
		catch (MalformedURLException e1) 
		{
			// If the URL request is malformed, treat it is a failed connection.
			handleFailedConnection( e1);
		}
		*/
	}
	
	
//	-------------------------------
//	  	  GETTERS & SETTERS
//	-------------------------------
	
	/** 
	 * These GETTERS allow access to the weather data. The data is READ ONLY. 
	 */
	public Location 		getLocation(){		return myLocation;		}
	public Units 			getUnits(){			return myUnits;			}
	public Weather 			getWeather(){		return myWeather;		}
	public Astronomy		getAstronomy(){		return myAstronomy;		}
	public Atmosphere		getAtmosphere(){	return myAtmosphere;	}
	public Wind				getWind(){			return myWind;			}
	public FiveDayForecast	getForecast(){		return myForecast;		}
	
	
//	-------------------------------
//	  	IF CONNECTION FAILS:
//	-------------------------------
	
	// This method is called if the URL fails to connect.
	// That may happen to the user, if internet is down, so it must be handled without breaking the program.
	private void handleFailedConnection( IOException e)
	{
		// TODO: Write handleFailedConnection method
	}
	
}


//	-------------------------------
//		  INTERNAL CLASSES
//	-------------------------------

// LOCATION
// The location of today's weather. All parameters taken from Yahoo XML
class Location{
	
	// Private VARIABLES
	private String city, region, country;
	private float latitude, longitude;
	
	// CONSTRUCTOR
	public Location( String city, String region, String country, float latitude, float longitude)
	{
		this.city = city;
		this.region = region;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	// Public GETTERS
	public String 	getCity(){			return city;		}
	public String 	getRegion(){		return region;		}
	public String 	getCountry(){		return country;		}
	public float 	getLatitude(){		return latitude;	}
	public float 	getLongitude(){		return longitude;	}
}

// UNITS
// Remembers what unit the measures are (F || C, Mi || Km, etc). All parameters taken from Yahoo XML 
class Units{
	
	// Private VARIABLES
	private String temperature, distance, pressure, speed;
	
	// CONSTRUCTOR 
	public Units( String temperature, String distance, String pressure, String speed)
	{
		this.temperature = temperature;
		this.distance = distance;
		this.pressure = pressure;
		this.speed = speed;
	}
	
	// Public GETTERS
	public String getTemperature(){		return temperature;	}
	public String getDistance(){		return distance;	}
	public String getPressure(){		return pressure;	}
	public String getSpeed(){			return speed;		}
}

// WEATHER
// Today's weather forecast. All parameters taken from Yahoo XML
class Weather{
	
	// Private VARIABLES
	private String condition, date;
	private int code;	// the weather conditions are number-coded. See the table for more info.
	private int temp;
	
	// CONSTRUCTOR
	public Weather( String condition, int code, int temp, String date)
	{
		this.condition = condition;
		this.code = code;
		this.temp = temp;
		this.date = date;
	}
	
	// Public GETTERS
	public String getCondition(){	return condition;	}
	public String getDate(){		return date;		}
	public int getCode(){			return code;		}
	public int getTemp(){			return temp;		}
}

// ASTRONOMY
// Today's astronomy data. All parameters taken from Yahoo XML
class Astronomy{
	
	// Private VARIABLES
	private Time sunrise, sunset;
	
	// CONSTRUCTOR
	public Astronomy( Time sunrise, Time sunset)
	{
		this.sunrise = sunrise;
		this.sunset = sunset;
	}
	
	// Public GETTERS
	public Time getSunrise(){	return sunrise;	}
	public Time getSunset(){	return sunset;	}
}

// ATMOSPHERE
// Today's atmosphere conditions. All parameters taken from Yahoo XML 
class Atmosphere{
	
	// Private VARIABLES
	private int humidity, visibility;
	private float pressure;
	private int rising; // state of the barometric pressure: steady (0), rising (1), or falling (2). 
	
	// CONSTRUCTOR
	public Atmosphere( int humidity, int visibility, float pressure, int rising)
	{
		this.humidity = humidity;
		this.visibility = visibility;
		this.pressure = pressure;
		this.rising = rising;
	}
	
	// Public GETTERS
	public int		getHumidity(){		return humidity;	}
	public int		getVisibility(){	return visibility;	}
	public float	getPressure(){		return pressure;	}
	public int		getRising(){		return rising;		}
}

// WIND
// Today's wind data. All parameters taken from Yahoo XML
class Wind{
	// Private VARIABLES
	private int chill, direction, speed;
	
	// CONSTRUCTOR
	public Wind( int chill, int direction, int speed)
	{
		this.chill = chill;
		this.direction = direction;
		this.speed = speed;
	}
	
	// Public GETTERS
	public int getChill(){		return chill;		}
	public int getDirection(){	return direction;	}
	public int getSpeed(){		return speed;		}
}
	
// FORECAST (1 DAY)
// The forecast of a day. Used for the 5-day forecast. All parameters taken from Yahoo XML
class ForecastDay{
	
	// Private VARIABLES
	private String day, condition;
	private int high, low;
	
	// CONSTRUCTOR
	public ForecastDay( String day, String condition, int high, int low)
	{
		this.day = day;
		this.condition = condition;
		this.high = high;
		this.low = low;
	}
	
	// Public GETTERS
	public String 	getDay(){			return day;			}
	public String 	getCondition(){		return condition;	}
	public int 		getHigh(){			return high;		}
	public int 		getLow(){			return low;			}
}

// FIVE DAY FORECAST
// The 5-day forecast. Made up of 5 ForecastDay objects, arranged in an array.
class FiveDayForecast{
	
	// Private VARIABLES
	private ForecastDay[] forecastArray = new ForecastDay[5];
	
	// CONSTRUCTOR
	public FiveDayForecast( ForecastDay today, ForecastDay day1, ForecastDay day2, ForecastDay day3, ForecastDay day4)
	{
		forecastArray[0] = today;
		forecastArray[1] = day1;
		forecastArray[2] = day2;
		forecastArray[3] = day3;
		forecastArray[4] = day4;
	}
	
	// Public GETTERS.
	
	/** FiveDayForcast.getForecastOf(int)
	 * Gets the forecast of a specific day. The day is index 0, with 0 being today.
	 * Valid days range from 0 - 4.
	 */
	public ForecastDay getForecastOf( int day){
			// ensure that the day value is valid.
		if( day >= 0 || day <= 4)
		{
			// return the ForecastDay object of the specified day.
			return forecastArray[ day];
		} else {
			// if the day range is invalid, return NULL.
			return null;
		}
	}
}