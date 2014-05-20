package app_weatherman.yahoo;

import java.io.IOException;

//FIVE DAY FORECAST
//The 5-day forecast. Made up of 5 ForecastDay objects, arranged in an array.
public class FiveDayForecast implements IYahooWeatherItem{
	
	// Private VARIABLES
	private ForecastDay[] forecastArray = new ForecastDay[5];
	
	// CONSTRUCTOR
	public FiveDayForecast( XMLReader weatherReader, String temperatureUnits) throws IOException
	{
		weatherReader.readFirstInstanceOf("<BR /><b>Forecast:");
		forecastArray[0] = new ForecastDay( weatherReader.readLine(), temperatureUnits);
		forecastArray[1] = new ForecastDay( weatherReader.readLine(), temperatureUnits);
		forecastArray[2] = new ForecastDay( weatherReader.readLine(), temperatureUnits);
		forecastArray[3] = new ForecastDay( weatherReader.readLine(), temperatureUnits);
		forecastArray[4] = new ForecastDay( weatherReader.readLine(), temperatureUnits);
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
