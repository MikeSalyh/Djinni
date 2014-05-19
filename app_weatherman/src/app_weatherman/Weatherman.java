package app_weatherman;

import java.net.*;
import java.io.*;

public class Weatherman {

	public static void main(String[] args) throws IOException{
		
		//URL url = new URL("http://weather.yahooapis.com/forecastrss?w=2354314");
		YahooWeatherData j = new YahooWeatherData( 2354314);
		
		/*BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

	    while (( reader.readLine() ) != null) {
			System.out.println(reader.readLine());
	    }
	    
	    reader.close();*/
		
		
	}

}
