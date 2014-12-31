package weatherman;
import java.util.Timer;
import java.io.IOException;

import io.APIKeyReader;

/**
 * WeatherMan gives live weather information about a location, 
 * gathered from the WeatherUnderground API. This program makes one API
 * call every 20 minutes. 
 * 
 * @author Mike
 * @version 0.3.0
 *
 */
public class WeatherMan {
	
	/**
	 * The version number of this application.
	 */
	public static final String VERSION = "0.3.0";
	
	/** 
	 * The file name of the Weather Underground API key 
	 */
	public static final String WEATHER_UNDERGROUND_KEY_NAME = "wu_key.txt";
	
	/** 
	 * Key to the WeatherUnderground API. It is read from a text file.
	 * @see APIKeyReader
	 */
	public static String api_key;
	
	public static void main(String[] args) throws InterruptedException{
		System.out.println("*** WEATHERMAN APP " + VERSION + " ***");
		System.out.println("");
		
		try{
			// Get the API key from the Djinni Program Data folder
			api_key = new APIKeyReader( WEATHER_UNDERGROUND_KEY_NAME).getKey();
			
			// Set up a timer that will gather Weather Data on a fixed interval
			Timer time = new Timer();
			WeatherTask wt = new WeatherTask(api_key);
			time.schedule(wt, 0, WeatherTask.INTERVAL);
			
		} catch( IOException e) {
			// If the API key can not be loaded, log it in the console. 
			// Note that the timer is not started, and URL connection is never attempted.
			System.out.println("Error: WeatherMan could not locate your API Key.");
			System.out.println("The API Key should be stored in " + APIKeyReader.ROOT_FILE_PATH + WEATHER_UNDERGROUND_KEY_NAME);
		}
	}
}
