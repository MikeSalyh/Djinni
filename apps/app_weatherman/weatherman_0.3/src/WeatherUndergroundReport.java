import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

/**
  * This class gathers and holds weather data. 
  * All information comes from the Weather Underground API.
  * 
  * @author Mike
  */
public class WeatherUndergroundReport {
	
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
	
	/** TRUE is this cbject has successfully connectected to the
	 * WU conditions report. False by default.
	 */
	private Boolean successful_connection_conditions = false;
	
	/** TRUE if this object has successfully connected to the
	 * WU three-day forecast. False by default
	 */
	private Boolean successful_connection_forecast = false;
	
//	 --- DATA VARIABLES -----------------------------------
	/** The location of the user's IP address */
	private String user_city;
	
	/** The current weather conditions ("Mostly clear", "Rainy", etc.) of the user's location */
	private String current_condition;
	
	/** The current temperature as a string, measured in Fahrenheit */
	private String current_temp;
	
	
//	 ******************************************************
//	                    PUBLIC METHODS 
//	 ******************************************************
	/**
	 * This class gathers and holds weather data.
	 * All information comes from the Weather Underground API, using the 
	 * location of the user's IP address.
	 * 
	 * @param api_key A key to the Weather Underground API.
	 * @throws IOException 
	 */
	public WeatherUndergroundReport( String api_key)
	{		
		this.api_key = api_key;
		
		// Connect to the API and gather data.
		// The gathered data is saved into this object.
		successful_connection_conditions = gatherDataFromConditionsReport();
	}
	
//	******************************************************
//    					GETTERS 
//	******************************************************
	
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
	
//	******************************************************
//   				   PRIVATE METHODS 
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
				throw new IOException();
		}
		
		URL url = new URL( API_REQUEST_BASE + api_key + report_type + API_REQUEST_TAIL);
		
		// Connect to the API, and retrieve a JSON object
		InputStream is = url.openStream();
		JsonReader rdr = Json.createReader(is);
		JsonObject obj = rdr.readObject();
		
		// Return the retrieved JSON object
		return obj;
	}
}




/* 
 * Example of how to read JSON:
 * 		URL url = new URL(api_request);
		InputStream is = url.openStream();
		JsonReader rdr = Json.createReader(is);
		JsonObject obj = rdr.readObject();
		
		JsonArray results = obj.getJsonObject("forecast").getJsonObject("simpleforecast").getJsonArray("forecastday");
		for( JsonObject result : results.getValuesAs(JsonObject.class))
		{
			String day = result.getJsonObject("date").getString("weekday_short");
			System.out.println(day);
		} 
  */
