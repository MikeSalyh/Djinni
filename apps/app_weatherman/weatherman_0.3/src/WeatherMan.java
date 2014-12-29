
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
	 * The file path of the Weather Underground API key
	 */
	private static final String WEATHER_UNDERGROUND_KEY_PATH = "wu_key.txt";
	
	public static void main(String[] args) {
		APIKeyReader kr = new APIKeyReader( WEATHER_UNDERGROUND_KEY_PATH);
		try{
			System.out.println(kr.getKey());
			System.out.println(kr.getKey());
		} catch( IOException e) {
			System.out.println( "FAILED TO READ FILE");
		}
	}

}
