package weatherunderground;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
  * This class gathers and holds weather data. 
  * All information comes from the Weather Underground API.
  * 
  * @author Mike
  */
public class WeatherReport {
	
//	 ******************************************************
//	                    CONSTS & VARS 
//	 ******************************************************
	
//	 --- URL CONSTANTS ------------------------------------
	/*
	 * These strings, combined with an API_KEY form an API request.
	 * WeatherMan uses two types of calls to gather its data: CONDITIONS and FORECAST.
	 * For full documentation, visit http://www.wunderground.com/weather/api/d/docs
	 */
	private static final String API_REQUEST_BASE = "http://api.wunderground.com/api/";
	private static final String API_REQUEST_TAIL = "/q/autoip.json";
	private static final String CONDITIONS = "/conditions";
	private static final String FORECAST = "/forecast";
	
//	 --- CONNECTION VARIABLES -----------------------------
	/** The WU API key of the user */
	private String api_key;
	
	/** TRUE is this cbject has successfully connected to the
	 * WU conditions report. False by default.
	 */
	private Boolean successful_connection_conditions = false;
	
	/** TRUE if this object has successfully connected to the
	 * WU three-day forecast. False by default
	 */
	private Boolean successful_connection_forecast = false;
	
	/**
	 * If there is an issue connecting to, or reading from
	 * the API, this string holds the error message.
	 */
	private String error_message;
	
//	 --- DATA VARIABLES -----------------------------------
	/** The location of the user's IP address */
	private String user_city;
	
	/** The current weather conditions ("Mostly clear", "Rainy", etc.) of the user's location */
	private String current_condition;
	
	/** The current temperature as a string, measured in Fahrenheit */
	private String current_temp;
	
	/** 
	 * An array that holds the three day forecast It has a length of
	 * four positions, because today's data is held in position [0]. 
	 */
	private DayForecast[] forecast = new DayForecast[4]; 
	
	
//	 ******************************************************
//	                  CONSTRUCTOR
//	 ******************************************************
	/**
	 * This class gathers and holds weather data.
	 * All information comes from the Weather Underground API, using the 
	 * location of the user's IP address.
	 * 
	 * @param api_key A key to the Weather Underground API.
	 * @throws IOException 
	 */
	public WeatherReport( String api_key)
	{		
		this.api_key = api_key;
		
		// Connect to the API and gather data.
		// The gathered data is saved into this object.
		
		// Two calls are made to the API. The first gathers today's conditions.
		successful_connection_conditions = gatherDataFromConditionsReport();
		// The second gathers the three day forecast.
		successful_connection_forecast = gatherDataFromForecastReport();
	}
	
	
//	******************************************************
//    					GETTERS 
//	******************************************************
	
	/**
	 * Whether or not this report was successfully generated.
	 * Always ensure that a report was successful before probing it
	 * for information.
	 * <p>
	 * Unsuccessful reports do not throw exceptions. 
	 * @return True if the report was successful. If there was an issue
	 * connecting to or reading from the WeatherUnderground API, this
	 * method will return false.
	 */
	public Boolean wasSuccessful(){
		return successful_connection_conditions && successful_connection_forecast;
	}
	
	/**If there was a connection error, this string holds it. Otherwise, null*/
	public String getErrorMessage(){
		return error_message;
	}
	
	/** The location of the user's IP address */
	public String getUserCity()
	{		
		return user_city;	
	}
	
	/** The current weather conditions. For example, "cloudy" or "mostly sunny"*/
	public String getCurrentCondition()
	{
		return current_condition;
	}
	
	/** The current temperature as a string, measured in Fahrenheit */
	public String getCurrentTemp()
	{
		return current_temp;
	}
	
	/**
	 * Get the forecast for a future day.
	 * @param day Between 0 (today) and 3 (three days from today).
	 * @return A DayForecast object for the requested day.
	 * @throws IllegalArgumentException If the requested day is out of range [0-3], throws an exception.
	 */
	public DayForecast getForecast( int day) throws IllegalArgumentException
	{
		if( day < 0 || day >= forecast.length)
			throw new IllegalArgumentException("Requested forecast is out of range. Valid range [0-" + (forecast.length-1) + "]. You said " + day);
		
		return forecast[day];
	}
	
	
//	******************************************************
//   			  DATA COLLECTING METHODS 
//	******************************************************	
	
	/**
	 * Gathers data from the WeatherUnderground Conditions report and 
	 * saves it into this object.
	 * <p>
	 * SETS THE FOLLOWING VARIABLES: user_city, current_condition, current_temp
	 * 
	 * @return True if the data collection was successful.
	 */
	private Boolean gatherDataFromConditionsReport()
	{
		try{
			// Connect to the API, and retrieve a conditions report
			JsonObject obj = connectToAPI(CONDITIONS);
			
			// Write the following variables to memory:
			user_city = obj.getJsonObject("current_observation").getJsonObject("display_location").getString("full");
			current_condition = obj.getJsonObject("current_observation").getString("weather");
			current_temp = obj.getJsonObject("current_observation").getJsonNumber("temp_f").toString() + "°F";
			
			return true;
		} catch( IOException e) {
			// If the API connection failed, return false
			error_message = "Could not connect to server. " + e.toString();
			return false;
		}
	}
	
	/**
	 * Gathers data from the WeatherUnderground Forecast report and 
	 * saves it into this object.
	 * <p>
	 * SETS THE FOLLOWING VARIABLES: user_city, current_condition, current_temp
	 * 
	 * @return True if the data collection was successful.
	 */
	private Boolean gatherDataFromForecastReport()
	{
		try{
			// Connect to the API, and retrieve a conditions report
			JsonObject obj = connectToAPI(FORECAST);
			
			// iterate thru the JSON array, and extract the forecast information.
			JsonArray results = obj.getJsonObject("forecast").getJsonObject("simpleforecast").getJsonArray("forecastday");
			for( JsonObject result : results.getValuesAs(JsonObject.class))
			{
				// The PERIOD represents how many days the forecast is from
				// the current day, with 1 being today.
				int period = result.getInt("period");
				
				// Since our forecast array is zero-based, we must subtract
				// 1 from the period.
				period -= 1;
				
				// before adding a DayForecast object to our forecast
				// array, ensure that it will fit into the array.
				if( period >= 0 && period < forecast.length)
				{
					// Write the three day forecast into memory
					String day_name = result.getJsonObject("date").getString("weekday_short");
					String condition = result.getString("conditions");
					String high = result.getJsonObject("high").getString("fahrenheit");
					String low = result.getJsonObject("low").getString("fahrenheit");
					
					forecast[period] = new DayForecast(day_name, condition, high, low);
				}
			}
			
			return true;
		} catch( IOException e) {
			// If the API connection failed, return false
			error_message = "Could not connect to server. " + e.toString();
			return false;
		}
	}
	
	/**
	 * Connects to the WeatherUnderground API, and returns a JSON object containing the
	 * requested weather report.
	 *  
	 * @param report_type The type of report. Use
	 * CONDITIONS, HOURLY, or FORECAST. Anything else will throw an 
	 * IO Exception.
	 * @return a JSON object containing the requested report
	 * @throws IOException If there is an issue connecting to the API
	 * @throws IOException If the param is invalid
	 */
	private JsonObject connectToAPI(String report_type) throws IOException
	{
		// First, ensure that the parameter is a valid type of report.
		switch(report_type){
			case CONDITIONS:
			case FORECAST:
				break;
			default:
				// If the parameter is not a valid report, throw an error.
				error_message = "Illegal arguement: " + report_type + ". Did not attempt to connect to the API.";
				throw new IllegalArgumentException();
		}
		
		URL url = new URL( API_REQUEST_BASE + api_key + report_type + API_REQUEST_TAIL);
		
		// Connect to the API, and retrieve a JSON object
		InputStream is = url.openStream();
		JsonReader rdr = Json.createReader(is);
		JsonObject obj = rdr.readObject();
		
		// Ensure that the API key is valid
		JsonObject validator = obj.getJsonObject("response").getJsonObject("error");
		if( validator != null)
		{
			// if there is an Error Object, the report was unsuccessful.
			String error_code = validator.getString("type");
			error_message = "The server says: " + error_code;
			throw new IOException(error_message);
		}
		
		// Return the retrieved JSON object
		return obj;
	}

}