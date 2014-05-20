package app_weatherman.yahoo;

import java.io.IOException;

// ASTRONOMY
// Today's astronomy data. All parameters taken from Yahoo XML
public class Astronomy implements IYahooWeatherItem{
	
	// Private VARIABLES
	private String sunrise, sunset;
	
	// CONSTRUCTOR
	public Astronomy( XMLReader weatherReader) throws IOException
	{
		String currentLine = weatherReader.readFirstInstanceOf("<yweather:astronomy");			
		this.sunrise = weatherReader.extract(currentLine, "sunrise");;
		this.sunset	 = weatherReader.extract(currentLine, "sunset");
	}
	
	// Public GETTERS
	public String getSunrise(){	return sunrise;	}
	public String getSunset(){	return sunset;	}
	
	// TO-STRING functions
	public String toStringSunrise(){	return "Sunrise: " + sunrise;	}
	public String toStringSunset(){		return "Sunset: " + sunset;		}
	
	public String toString()
	{
		return toStringSunrise() + ". " + toStringSunset() + ".";
	}
}