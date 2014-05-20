package app_weatherman.yahoo;

import java.io.IOException;

// WEATHER
// Today's weather forecast. All parameters taken from Yahoo XML
public class Weather implements IYahooWeatherItem {	
	
	// Private VARIABLES
	private String temperatureUnits;
	private String condition, date;
	private int code;	// the weather conditions are number-coded. See the table for more info.
	private int temp;
		
	// CONSTRUCTOR
	public Weather( XMLReader weatherReader, String temperatureUnits) throws IOException
	{	
		this.temperatureUnits = "¡" + temperatureUnits;
		
		String currentLine = weatherReader.readFirstInstanceOf("<yweather:condition");				
		this.condition 	= weatherReader.extract( currentLine, "text");
		this.code 		= Integer.parseInt( weatherReader.extract(currentLine, "code"));
		this.temp 		= Integer.parseInt(	weatherReader.extract(currentLine, "temp"));
		this.date 		= weatherReader.extract( currentLine, "date");
	}
		
	// Public GETTERS
	public String getCondition(){	return condition;	}
	public String getDate(){		return date;		}
	public int getCode(){			return code;		}
	public int getTemp(){			return temp;		}
	
	// TO-STRING methods
	public String toStringCondition(){	return condition;				}
	public String toStringDate(){		return date;					}
	public String toStringTemp(){		return temp + temperatureUnits;	}
	
	public String toString()
	{
		return toStringTemp() + ", " + condition + " (" + date + ")";
	}
}