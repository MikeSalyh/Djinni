package weatherunderground;
/**
 * This class represents the weather forecast of a single day.
 * @author Mike
 *
 */

public class DayForecast {

//	******************************************************
//   				 CONSTS & VARS 
//	******************************************************
	
	/** The name of the day */
	private String day_name;
	
	/** The weather conditions for the day */
	private String condition;
	
	/** The daily high temp, measured in Fahrenheit */
	private String high;
	
	/** The daily low temp, measured in Fahrenheit */
	private String low;
	
//	******************************************************
//	 				GETTERS 
//	******************************************************
	
	/** The name of the day, ie "Mon", "Tues", etc.*/
	public String getDayName()
	{
		return day_name;
	}
	
	/** The weather conditions for the day */
	public String getCondition()
	{
		return condition;
	}
	
	/** The daily high temp, measured in Fahrenheit */
	public String getHigh()
	{
		return high;
	}
	
	/** The daily low temp, measured in Fahrenheit */
	public String getLow()
	{
		return low;
	}

//	******************************************************
//				 METHODS 
//	******************************************************
	
	/**
	 * This object represents a simple day's forecast. The information
	 * 
	 * @param name The day name, ie "Mon", "Tues", etc.
	 * @param condition The weather conditions, ie "Clear", "Cloudy", etc.
	 * @param high The daily high temp in Fahrenheit, as a String. Do not add degrees symbol.
	 * @param low The daily low temp in Fahrenheit, as a String. Do not add degrees symbol.
	 */
	public DayForecast( String day_name, String condition, String high, String low)
	{
		this.day_name = day_name;
		this.condition = condition;
		this.high = high + "°F";
		this.low = low + "°F";
	}
	
	public String toString()
	{
		return getDayName() + ": " + getCondition() + ". High: " 
					+ getHigh() + ", Low: " + getLow();
	}
}
