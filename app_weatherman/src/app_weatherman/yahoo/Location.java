package app_weatherman.yahoo;

import java.io.IOException;

// LOCATION
// The location of today's weather. All parameters taken from Yahoo XML
public class Location implements IYahooWeatherItem{
	
	// Private VARIABLES
	private String city, region, country;
	
	// CONSTRUCTOR
	public Location( XMLReader weatherReader) throws IOException
	{
		// Iterate thru the XML until we find the correct section, then assign it to currentLine.
		String currentLine = weatherReader.readFirstInstanceOf("<yweather:location");		
		this.city 		= weatherReader.extract(currentLine, "city");
		this.region 	= weatherReader.extract(currentLine, "region");
		this.country 	= weatherReader.extract(currentLine, "country");
	}
	
	// Public GETTERS
	public String 	getCity(){			return city;		}
	public String 	getRegion(){		return region;		}
	public String 	getCountry(){		return country;		}		
}
