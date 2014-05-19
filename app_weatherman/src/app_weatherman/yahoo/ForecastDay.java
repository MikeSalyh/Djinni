package app_weatherman.yahoo;

// FORECAST (1 DAY)
// The forecast of a day. Used for the 5-day forecast. All parameters taken from Yahoo XML
public class ForecastDay{
	
	// Private VARIABLES
	private String day, condition;
	private int high, low;
	
	// CONSTRUCTOR
	public ForecastDay( String day, String condition, int high, int low)
	{
		this.day = day;
		this.condition = condition;
		this.high = high;
		this.low = low;
	}
	
	// Public GETTERS
	public String 	getDay(){			return day;			}
	public String 	getCondition(){		return condition;	}
	public int 		getHigh(){			return high;		}
	public int 		getLow(){			return low;			}
}
