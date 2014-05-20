package app_weatherman.yahoo;

import java.io.IOException;

// WIND
// Today's wind data. All parameters taken from Yahoo XML
public class Wind implements IYahooWeatherItem{
	
	// Private VARIABLES
	private String temperatureUnits, speedUnits;
	private int chill, direction, speed;
	
	// CONSTRUCTOR
	public Wind( XMLReader weatherReader, String temperatureUnits, String speedUnits) throws IOException
	{
		this.temperatureUnits 	= "¡" + temperatureUnits;
		this.speedUnits 		= speedUnits;
		
		String currentLine = weatherReader.readFirstInstanceOf("<yweather:wind");		
		this.chill 		= Integer.parseInt(	weatherReader.extract(currentLine, "chill"));
		this.direction 	= Integer.parseInt(	weatherReader.extract(currentLine, "direction"));
		this.speed 		= Integer.parseInt(	weatherReader.extract(currentLine, "speed"));
	}
	
	// Public GETTERS
	public int getChill(){		return chill;		}
	public int getDirection(){	return direction;	}
	public int getSpeed(){		return speed;		}
	
	// TO-STRING methods
	public String toStringChill(){		return "Wind Chill: " + chill + temperatureUnits; 	}
	public String toStringDirection(){	return "Wind Direction: " + direction + "¡"; 		}
	public String toStringSpeed(){		return "Wind Speed: " + speed + speedUnits;			}
	
	public String toString()
	{
		return toStringChill() + ", " + toStringDirection() + ", " + toStringSpeed() + ".";
	}
}