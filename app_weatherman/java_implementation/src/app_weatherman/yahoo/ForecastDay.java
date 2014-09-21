package app_weatherman.yahoo;

// FORECAST (1 DAY)
// The forecast of a day. Used for the 5-day forecast. All parameters taken from Yahoo HTML
public class ForecastDay implements IYahooWeatherItem{
	
	// Private VARIABLES
	private String temperatureUnits;
	private String day, condition;
	private int high, low;
	
	// CONSTRUCTOR
	// Takes a line of HTML from the WeatherReader, and parses it. 
	// For example: 	Wed - Partly Cloudy. High: 76 Low: 53<br />
	public ForecastDay( String HTMLString, String temperatureUnits)
	{
		this.temperatureUnits = "¡" + temperatureUnits;
		
		// First, parse the name of the day
		day = HTMLString.substring(0, 3);
		
		// Then, the weather condition. 
		int endOfCondition = HTMLString.indexOf('.') + 1;
		condition = HTMLString.substring(6, endOfCondition);
		
		// Next, the high temperature
		int startOfHigh = HTMLString.indexOf(":") + 2;
		HTMLString = HTMLString.substring(startOfHigh);	// cut down the HTML String, because the test for high and low is the same
		int endOfHigh = HTMLString.indexOf(" ");
		high = Integer.parseInt(HTMLString.substring(0, endOfHigh));
		
		// Last, the low temperature
		int startOfLow = HTMLString.indexOf(":") + 2;
		HTMLString = HTMLString.substring( startOfLow);
		int endOfLow = HTMLString.indexOf("<");
		low = Integer.parseInt(HTMLString.substring(0, endOfLow));		
	}
	
	// Public GETTERS
	public String 	getDay(){			return day;			}
	public String 	getCondition(){		return condition;	}
	public int 		getHigh(){			return high;		}
	public int 		getLow(){			return low;			}
	
	// TO-STRING methods
	public String toStringDayAndCondition(){	return day + ": " + condition;				}
	public String toStringHigh(){				return "High: " + high + temperatureUnits;	}
	public String toStringLow(){				return "Low: " + low + temperatureUnits;	}
	
	public String toString()
	{
		return toStringDayAndCondition() + " " + toStringHigh() + ", " + toStringLow() + ".";
	}
}
