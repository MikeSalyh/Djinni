package app_weatherman.yahoo;

/** 
 * The YahooWeatherData class represents information about the weather in a specific location.
 * It is built from the Yahoo Weather API, documentation for which can be found here: https://developer.yahoo.com/weather/
 * @author mikesalyh
 */

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class YahooWeatherData {
	
//	-------------------------------
//		VARIABLES & CONSTANTS 
//	-------------------------------

//	the API request, sans desired city. Append a WOEID to this URL to make a valid request 
	private static final String URL_BASE = "http://weather.yahooapis.com/forecastrss?w=";
	
//	Objects that hold the weather data. 
	private Location 		myLocation;
	private Units 			myUnits; 		
	private Weather 		myWeather; 		
	private Astronomy 		myAstronomy; 	
	private Atmosphere 		myAtmosphere; 	
	private Wind 			myWind;			
	private FiveDayForecast myForecast; 		

	
//	-------------------------------
//			  CONSTRUCTOR
//	-------------------------------
	
	/** 
	 * CONSTRUCTOR. 
	 * Requires a WOEID for the desired location. WOEID is a proprietary location
	 * ID developed by Yahoo. Each place on Earth has a unique WOEID.
	 * For more information, see https://developer.yahoo.com/geo/geoplanet/guide/concepts.html
	 * To lookup the WOEID of a place, use http://woeid.rosselliot.co.nz/
	 */ 
	public YahooWeatherData( int WOEID)
	{
		/* First, connect to the Yahoo Weather API and retrieve
		 * an XML object that contains all the weather data.
		 */
		
		InputStream apiStream;
		try{ 
			apiStream = openAPIStream( WOEID);			
		}
		catch (IOException connectionIssue)
		{
			/* If the URL request is malformed, or the user's computer cannot 
			 * connect to the Yahoo Weather API, handle the failed connection.
			 * This is within normal operating conditions, so it cannot be allowed
			 * to throw an exception!
			 */
			handleFailedConnection( connectionIssue);
			return;
		}	

		
		/* Then, take the information from the XML document, and save it into
		 * this object.
		 */
		try{
			gatherXMLData( apiStream);
		}
		catch (IOException readingIssue)
		{
			/* If there is an issue reading the XML file, or if the weather
			 * report is absent (because the requested city doesn't exist, for
			 * example), handle that error.
			 */
			handleBadXML( readingIssue);
			return;
		}

	}
	
	
	
//	-------------------------------
//	  	  GETTERS & SETTERS
//	-------------------------------
	
	/** 
	 * These GETTERS allow access to the weather data. The data is READ ONLY. 
	 */
	public Location 		getLocation(){		return myLocation;		}
	public Units 			getUnits(){			return myUnits;			}
	public Weather 			getWeather(){		return myWeather;		}
	public Astronomy		getAstronomy(){		return myAstronomy;		}
	public Atmosphere		getAtmosphere(){	return myAtmosphere;	}
	public Wind				getWind(){			return myWind;			}
	public FiveDayForecast	getForecast(){		return myForecast;		}
	
	
//	-------------------------------
//	  	CONNECTION METHODS
//	-------------------------------
	
//	Open a stream to the Yahoo Weather API.
	private InputStream openAPIStream( int WOEID) throws IOException
	{
		// First, create a URL request to the Yahoo Weather API.
		URL apiRequest = createAPIRequest( WOEID);
		
		// Then, open an input stream with the URL, and return it.
		return apiRequest.openStream();
	}
	
	
//	Creates a URL to the Yahoo Weather API using a given WOEID.
	private URL createAPIRequest( int WOEID) throws MalformedURLException
	{
		// Combine the URL base with the provided WOEID to form the API request.
		return new URL( URL_BASE + WOEID);
	}
	
	
/*	HANDLE FAILED CONNECTION	
 *	This method is called if the user fails to connect to the Yahoo Weather API.
 *	That may happen if the user's Internet is down, so it must be handled without breaking the program.
 */
	private void handleFailedConnection( IOException e)
	{
		// TODO: Write handleFailedConnection method
		System.out.print("Failed to connect to Yahoo Weather API.");
	}
	
	
//	-------------------------------
//  	    XML METHODS
//	-------------------------------

	/* GATHER XML DATA
	 * Take a Yahoo Weather XML document, and gather the information from it.
	 * That information is saved into private objects contained within this
	 * class. 
	 * 
	 * This method throws IO Exceptions if the buffered reader fails to properly 
	 * convert the XML, or if the Yahoo page gives an error. (For example, if the
	 * original WOEID was invalid).
	 */
	private void gatherXMLData( InputStream apiStream) throws IOException
	{
		XMLReader 	weatherReader; 	// Reads the XML data provided by the API.
		final int 	CHARACTER_BUFFER = 4000;
		/* The weatherReader requires a # of buffered characters, so that it can be 
		 * reset. A Yahoo weather report is about 3,000 characters long. We buffer
		 * 4,000 characters, to be safe. 
		 */
		
		// Create + mark the weatherReader. It is now ready to read.
		weatherReader = new XMLReader( ( new InputStreamReader( apiStream)));
		weatherReader.mark( CHARACTER_BUFFER);

		
		// First, check to ensure that the weather report has no errors.
		// The most common cause of an error is a bad WOEID.
		if( hasYahooError( weatherReader)){
			weatherReader.close();
			throw new IOException();
		}
		
		
		/* Then, gather all the weather information from the XML document.
		 * The order of these calls reflects the order they appear in the XML document.
		 * DO NOT CHANGE THE ORDER OF THESE CALLS!
		 */
		myLocation 		= new Location( weatherReader); 
		myUnits 		= new Units( weatherReader);
		myWind			= new Wind( weatherReader);
		myAtmosphere	= new Atmosphere( weatherReader);
		myAstronomy		= new Astronomy( weatherReader);
		myWeather		= new Weather( weatherReader);
		myForecast		= new FiveDayForecast( weatherReader);
		
		weatherReader.close();
	}
	
	// Checks for Yahoo Weather errors. 
	private boolean hasYahooError( BufferedReader weatherReader) throws IOException
	{
		String currentLine;
		
		// Iterate through the XML file. Search for the word "Error"
		while (( currentLine = weatherReader.readLine() ) != null) {
			if( currentLine.contains("Error") || currentLine.contains("error"))
			{
				weatherReader.reset();
				return true;
			} 
			else if( currentLine.contains("<title>"))
			{
				/* the error occurs in the title line. If the title line does not contain
				 * the error, the document is safe. */
				weatherReader.reset();
				return false;
			}
				
		}
		weatherReader.reset();
		return false;
	}
	
	
	/*	HANDLE BAD XML	
	 *	This method is called if the XML document has an issue. For example, an
	 *	invalid city.
	 */
	private void handleBadXML( IOException e)
	{
		// TODO: Write handleFailedConnection method
		System.out.print("There was an error in the Yahoo XML. Ensure that your WOEID is valid.");
	}
}