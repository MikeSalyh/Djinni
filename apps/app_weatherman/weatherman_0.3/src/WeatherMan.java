
import java.io.IOException;

import weatherunderground.WeatherReport;
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
	 * The file path of the Weather Underground API key
	 */
	private static final String WEATHER_UNDERGROUND_KEY_NAME = "wu_key.txt";
	
	public static void main(String[] args) {
		
		try{
			String api_key = new APIKeyReader( WEATHER_UNDERGROUND_KEY_NAME).getKey();
			WeatherReport wur = new WeatherReport(api_key);
			
			if( wur.wasSuccessful())
				System.out.println( wur.getForecast(1));
			else
				System.out.println( wur.getErrorMessage());
			
		} catch( IOException e) {
			System.out.println("Error: WeatherMan could not locate your API Key.");
			System.out.println("The API Key should be stored in C:\\ProgramData\\Djinni\\keys\\" + WEATHER_UNDERGROUND_KEY_NAME);
		}
	}

}
