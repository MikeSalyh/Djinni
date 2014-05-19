package app_weatherman.yahoo;

//FIVE DAY FORECAST
//The 5-day forecast. Made up of 5 ForecastDay objects, arranged in an array.
public class FiveDayForecast{
	
	// Private VARIABLES
	private ForecastDay[] forecastArray = new ForecastDay[5];
	
	// CONSTRUCTOR
	public FiveDayForecast( ForecastDay today, ForecastDay day1, ForecastDay day2, ForecastDay day3, ForecastDay day4)
	{
		forecastArray[0] = today;
		forecastArray[1] = day1;
		forecastArray[2] = day2;
		forecastArray[3] = day3;
		forecastArray[4] = day4;
	}
	
	// Public GETTERS.
	
	/** FiveDayForcast.getForecastOf(int)
	 * Gets the forecast of a specific day. The day is index 0, with 0 being today.
	 * Valid days range from 0 - 4.
	 */
	public ForecastDay getForecastOf( int day){
			// ensure that the day value is valid.
		if( day >= 0 || day <= 4)
		{
			// return the ForecastDay object of the specified day.
			return forecastArray[ day];
		} else {
			// if the day range is invalid, return NULL.
			return null;
		}
	}
}
