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
	
	// TO-STRING methods
	// these will most likely never be used.
	
	public String toStringTemperature(){	return "Temperature is measured in " + temperature;	}
	public String toStringDistance(){		return "Distance is measured in " + distance;		}
	public String toStringPressure(){		return "Pressure is measured in " + pressure;		}
	public String toStringSpeed(){			return "Wind Speed is measured in " + speed;		}

	public String toString(){
		return "This YahooWeatherData object measures using: " + temperature + ", " + distance + ", " + pressure + ", and " + speed + ".";
	}
}