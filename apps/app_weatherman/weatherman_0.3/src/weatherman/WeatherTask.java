package weatherman;

import java.util.TimerTask;
import java.util.Date;

/**
 * This class is the recurring event that gathers weather data.
 * It is a subclass of java.utils.TimerTask.
 * 
 * @see TimerTask
 * @author Mike
 *
 */
public class WeatherTask extends TimerTask {
	
//	 ******************************************************
//    					VARS & CONSTS
//	 ******************************************************
	
	/** How often WeatherReports are generated. 20 minutes in miliseconds. */
	public static final int INTERVAL = 1200000;
	
	/** the current date. Used to display time */
	private Date now;
	
	/** the key to access the WU API */
	private String api_key;
	
	/**
	 * The WeatherReport object that's created when generateWeatherReport() is called.
	 */
	private WeatherReport weather_report;
	
	
//	 ******************************************************
//				    CONSTRUCTOR & RUN METHOD
//	 ******************************************************
	
	/**
	 * A WeatherTask object. When run, the WeatherReport object connects to the
	 * WeatherUnderground API and gathers data. Then, it outputs it into the console.
	 * 
	 * @param api_key The key to access the WeatherUnderground API
	 */
	public WeatherTask( String api_key)
	{
		super();
		this.api_key = api_key;
	}
	
	/**
	 * Generates a WeatherReport, and outputs it into the console.
	 */
	public void run()
	{
		weather_report = new WeatherReport( api_key);
		printWeatherReport();
	}
	
	
//	 ******************************************************
//    				   PRINT REPORTS
//	 ******************************************************
	
	/**
	 * Logs the Weather Report into the console if it was successfully generated.
	 * <p>
	 * Logs error data into the console if the report was unsuccessfully generated.
	 *
	 * @see WeatherReport
	 */
	private void printWeatherReport()
	{
		if( weather_report.wasSuccessful())
		{
			//If the weather report was successful, log it in the console.
			System.out.println( weather_report.getUserCity());
			System.out.println( weather_report.getCurrentCondition() + ". " + weather_report.getCurrentTemp());
			System.out.println("");
			System.out.println("   " + weather_report.getForecast(0));
			System.out.println("   " + weather_report.getForecast(1));
			System.out.println("   " + weather_report.getForecast(2));
			System.out.println("   " + weather_report.getForecast(3));
		}
		else
		{
			// If there was an error generating the weather report,
			// log it in the console. This is not exceptional behavior.
			System.out.println("Error generating Weather Report.");
			System.out.println( weather_report.getErrorMessage());
		}
		
		// Log the time at the end of the WeatherReport.
		now = new Date();
		System.out.println("");
		System.out.println("Timestamp: " + now);
		
		// And buffer character
		System.out.println("*************************************");
	}
}
