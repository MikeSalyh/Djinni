package app_weatherman.yahoo;

import java.io.IOException;

// UNITS
// Remembers what unit the measures are (F || C, Mi || Km, etc). All parameters taken from Yahoo XML 
public class Units implements IYahooWeatherItem{
	
	// Private VARIABLES
	private String temperature, distance, pressure, speed;
	
	// CONSTRUCTOR 
	public Units( XMLReader weatherReader) throws IOException
	{
		String currentLine = weatherReader.readFirstInstanceOf("<yweather:units");		
		this.temperature 	= weatherReader.extract(currentLine, "temperature");
		this.distance 		= weatherReader.extract(currentLine, "distance");
		this.pressure 		= weatherReader.extract(currentLine, "pressure");
		this.speed 			= weatherReader.extract(currentLine, "speed");
	}
	
	// Public GETTERS
	public String getTemperature(){		return temperature;	}
	public String getDistance(){		return distance;	}
	public String getPressure(){		return pressure;	}
	public String getSpeed(){			return speed;		}
}