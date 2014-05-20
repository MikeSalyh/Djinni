package app_weatherman.yahoo;

import java.io.IOException;

// ATMOSPHERE
// Today's atmosphere conditions. All parameters taken from Yahoo XML 
public class Atmosphere implements IYahooWeatherItem{
	
	// Private VARIABLES
	private String pressureUnits, distanceUnits;
	private int humidity, visibility;
	private float pressure;
	private int rising; // state of the barometric pressure: steady (0), rising (1), or falling (2). 
	
	// CONSTRUCTOR
	public Atmosphere(  XMLReader weatherReader, String pressureUnits, String distanceUnits) throws IOException
	{
		this.pressureUnits = pressureUnits;
		this.distanceUnits = distanceUnits;
		
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
	
	
	// TO-STRING methods
	public String toStringHumidity(){	return "Humidity: " + humidity + "%";	}
	
	// TODO: Check this method. I don't know if the visibility # is correct in miles.
	public String toStringVisibility()
	{
		float trueVisibility 	= visibility / 100;
		String cleanVisibility;
		
		if(trueVisibility == (int) trueVisibility)
			cleanVisibility = String.format("%d", (int)trueVisibility);
	    else
	    	cleanVisibility = String.format("%s", trueVisibility);
		
		return "Visibility: " + cleanVisibility + distanceUnits;
	}
	
	public String toStringPressure()
	{
		String cleanPressure;
		if(pressure == (int) pressure)
			cleanPressure = String.format("%d", (int)pressure);
	    else
	    	cleanPressure = String.format("%s", pressure);
		
		return "Pressure: " + cleanPressure + pressureUnits;
	}
	
	public String toStringRising()
	{
		switch( rising){
		case 0:		return "Pressure is steady.";
		case 1: 	return "Pressure is rising.";
		case 2:		return "Pressure is falling.";
		default:	return "Pressure change unknown.";
		}
	}
	
	public String toString()
	{
		String pressureRising;
		switch( rising){
		case 0:		pressureRising = " (steady)."; 	break;
		case 1:	 	pressureRising = " (rising)."; 	break;
		case 2: 	pressureRising = " (falling).";	break;
		default: 	pressureRising = ".";			break;
		}
		
		return toStringHumidity() + ". " + toStringVisibility() + ". " + 
			toStringPressure() + pressureRising;
 	}
}