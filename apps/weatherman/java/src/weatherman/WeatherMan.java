package weatherman;
import java.util.Timer;
import java.io.IOException;

import io.OsUtils;
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
	
	// TODO: Failed connection calls should try again, fast.
	
//	******************************************************
//					TOP LEVEL DJINNI
//	******************************************************
	
	/**
	 * The version number of this application.
	 */
	public static final String VERSION = "0.3.1";
	
	/**
	 * The root folder of the Djinni Home AI System's program data
	 */
	public static final String win_ROOT_FILE_PATH = "C:\\ProgramData\\Djinni\\";
	public static final String mac_ROOT_FILE_PATH = "\\Applications\\Djinni\\";
	
	/**
	 * The root folder of the Djinni Home AI System.
	 * Set after the OS has been determined.
	 */
	public static String ROOT_FILE_PATH;
	
//	******************************************************
//					WEATHERMAN APP
//	******************************************************
	
	
	/** 
	 * The file name of the Weather Underground API key 
	 */
	public static final String WEATHER_UNDERGROUND_KEY_NAME = "wu_key.txt";
	
	/**
	 * The file name the WeatherReports will be saved under
	 */
	public static final String WEATHER_REPORT_FILE_NAME = "wu_report.txt";
	
	/** 
	 * Key to the WeatherUnderground API. It is read from a text file.
	 * @see APIKeyReader
	 */
	private static String api_key;
	
	public static void main(String[] args) throws InterruptedException{
		System.out.println("*** WEATHERMAN APP " + VERSION + " ***");
		System.out.println("");
		
		// Check the operating system, and choose the correct filepath:
		if( OsUtils.isWindows()) 
		{
			ROOT_FILE_PATH = win_ROOT_FILE_PATH;
		} else if( OsUtils.isMac()) {
			ROOT_FILE_PATH = mac_ROOT_FILE_PATH;
		} else {
			// If the user's operating system is not supported, go no further.
			System.out.println("**ERROR: Unsupported Operating System");
			return;
		}
			
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
			System.out.println("The API Key should be stored in " + ROOT_FILE_PATH + WEATHER_UNDERGROUND_KEY_NAME);
		}
	}
}
