package app_weatherman.yahoo;

import java.io.IOException;

// ATMOSPHERE
// Today's atmosphere conditions. All parameters taken from Yahoo XML 
public class Atmosphere implements IYahooWeatherItem{
	
	// Private VARIABLES
	private int humidity, visibility;
	private float pressure;
	private int rising; // state of the barometric pressure: steady (0), rising (1), or falling (2). 
	
	// CONSTRUCTOR
	public Atmosphere(  XMLReader weatherReader) throws IOException
	{
		// Iterate thru the XML until we find the correct section, then assign it to currentLine.
		String currentLine = weatherReader.readFirstInstanceOf("<yweather:atmosphere");			
		this.humidity 	= Integer.parseInt( weatherReader.extract(currentLine, "humidity"));;
		this.visibility = Integer.parseInt( weatherReader.extract(currentLine, "visibility"));
		this.pressure	= Float.parseFloat( weatherReader.extract(currentLine, "pressure"));
		this.rising 	= Integer.parseInt( weatherReader.extract(currentLine, "rising"));
	}
	
	// Public GETTERS
	public int		getHumidity(){		return humidity;	}
	public int		getVisibility(){	return visibility;	}
	public float	getPressure(){		return pressure;	}
	public int		getRising(){		return rising;		}
}