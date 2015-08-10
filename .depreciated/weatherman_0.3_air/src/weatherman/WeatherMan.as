package weatherman {
	import flash.display.MovieClip;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.filesystem.File;
	import flash.events.IOErrorEvent;
	import flash.utils.Timer;
	import flash.events.TimerEvent;
	import flash.display.StageDisplayState;
	import flash.system.Capabilities;

	/** 
	* The main class for the Flash Weatherman component.
	* It gathers information from a text file, and displays it on screen.
	* This .swf does NOT connect to the internet! All data is taken from
	* the user's harddrive.
	*
	* This .swf is to be used in tandem with Weatherman.jar
	* @author: Mike
	*/
	
	/* MovieClip Structure:
	*
	*	this
	*	|
	*	|- clock (ClockMC)
	*	|- conditions (ConditionMC)
	*	|- forecast1 (ForecastMC)
	*	|- forecast2 (ForecastMC)
	*	|- forecast3 (ForecastMC)
	*	|- version (MovieClip)
	*	|	|- versionNumber (TextField)
	*	|- timeStamp (TextField)
	*
	*/
	
	public class WeatherMan extends MovieClip implements IWeatherDisplay{
		
		// *****************************************************
		// 						DJINNI
		// *****************************************************
		
		public static const PROGRAM_NAME:String = "Djinni Home AI";
		public static const VERSION:String = "v.0.3.1";
		
		/** This points to C:// on a windows machine.
		* Points to / on mac.
		*/
		public var ROOT_FILE_PATH:String = (File.getRootDirectories()[0] as File).url;
		
		/** The directory where all Djinni application data is held.  */
		public const win_PROGRAM_DATA_DIRECTORY:String = "ProgramData/Djinni/app_data/";
		public const mac_PROGRAM_DATA_DIRECTORY:String = "Applications/Djinni/app_data/";
		
		// *****************************************************
		// 						WEATHERMAN
		// *****************************************************
		
		// --- VARS & CONSTS -----------------------------------
		
		// This string points the location of the weather report text file.
		private var report_location:String;
			
		// The directory where the WeatherMan app's data is held.
		public static const WEATHERMAN_FOLDER:String = "weatherman/";
		
		// The file name of the weather report text file.
		public static const WEATHER_REPORT_FILE_NAME:String = "wu_report.txt";
		
		// The footer begins with this, and ends with a timestamp.
		private static const REPORT_FOOTER:String = "This report was generated ";
		
		/** The text file that will be loaded */
		private var report:File;
		
		/** The object where weather reports are saved into local memory */
		private var weatherReport:WeatherReport;
		
		/** The event that's dispatched when a WeatherReport is successfully parsed */
		private static const SUCCESSFUL_READ_REPORT:String = "successful";
		
		/** the event that's dispatched when a WeatherReport cannot be parsed */
		private static const UNSUCCESSFUL_READ_REPORT:String = "unsuccessful";
		
		/** this string is set whenever the program encounters an error */
		private var errorMessage:String;
		
		/** Timer that will refresh the report every minute. NOTE that this
		* app doesn't connect to the internet, it only reads from drive. */
		private var reportTimer:Timer;

		// --- CONSTRUCTOR ----------------------------------------
		
		/**
		* This SWF reads data from a text file, and displays it on the screen.
		* It is the visual component of the WeatherMan app.
		*/
		public function WeatherMan() {
			// set the version number
			version.versionNumber.text = PROGRAM_NAME + " " + VERSION;
			
			// Start the program in safe mode.
			enterSafeMode();
			
			try{
				// Ensure that the operating system is valid, and perform all necessary actions on it.
				performOSExclusiveActions();
			} catch( e:Error) {
				// If the operating system is unsupported, do not try to read reports
				trace("**Your Operating system is not supported. Djinni is Windows/Mac only");
				if( errorMessage == null)
					errorMessage = "Unsupported Operating System.";
				enterErrorMode();
				return;
			}
			
			// Start the program in fullscreen
			stage.displayState = StageDisplayState.FULL_SCREEN; 
			
			// Set up listeners for after a report is read
			addEventListener( SUCCESSFUL_READ_REPORT, displayReport);
			addEventListener( UNSUCCESSFUL_READ_REPORT, enterErrorMode);
			
			// The report timer will go off once every minute, and update the report.
			reportTimer = new Timer( 60000, 0);
			reportTimer.addEventListener(TimerEvent.TIMER, loadReport);
			reportTimer.start();
			
			// And one load report on start-up.
			loadReport();
		}
		
		/** This method does any action that is Operating-System dependent.
		* @throws Error if the user's OS is unsupported, or an operation fails */
		private function performOSExclusiveActions():void
		{
			// Calculate the filepath to report file, based on the user's OS:
			if((Capabilities.os.indexOf("Windows") >= 0))
			{
				 // in windows
				 report_location = ROOT_FILE_PATH + win_PROGRAM_DATA_DIRECTORY + WEATHERMAN_FOLDER + WEATHER_REPORT_FILE_NAME
			}
			else if((Capabilities.os.indexOf("Mac") >= 0)){
				// in mac
				report_location = ROOT_FILE_PATH + mac_PROGRAM_DATA_DIRECTORY + WEATHERMAN_FOLDER + WEATHER_REPORT_FILE_NAME; 
			} else {
				// Throw an error if neither Windows or Mac. Linux support may come later...
				throw new Error("Bad OS");
			}
		}
		
		// --- REPORT LOADING, READING & PARSING ----------------------------
		/**
		* Starts loading a weather report. Reading from hard drive is an async
		* operation, so this method sets up an event that will trigger when the load
		* completes.
		* @see readReport
		*/
		private function loadReport( e:Event = null):void{			
			trace("..loading weather report..");
			
			// Set up the "report" file. This is the text file
			// that weatherman will read from.
			report = new File();
			report.url = report_location;
			report.addEventListener(Event.COMPLETE, readReport);
			report.addEventListener( IOErrorEvent.IO_ERROR, handleIOError);
			report.load();
		}
		
		/**
		* Read the text file, parse its JSON, and display its information
		* on screen. This method is only reached if the report text file
		* was successfully loaded.
		*
		* @Event 
		*/
		private function readReport( e:Event = null):void
		{			
			trace("..reading weather report..");

			// When the report has finished loading, read its data into a string
			var jsonString:String = report.data.toString();
			
			// Then, convert that String into an Object
			try{
				var jsonReport:Object = JSON.parse( jsonString);
			} catch( e:Error) {
				// If there is an error parsing the JSON report,
				// do not break the program. Instead, handle the issue,
				// then enter error mode.
				if( errorMessage == null)
					errorMessage = "Invalid JSON Error.";
				handleIOError( e);
				return;
			}
			
			if( validateJsonReport(jsonReport))
			{
				// If the report is valid, parse it, and save it into this object.
				parseJsonReport( jsonReport);
				dispatchEvent( new Event( SUCCESSFUL_READ_REPORT));
			} else {
				// If the report was invalid or unsuccessful,
				// it does not get parsed.
				if( errorMessage == null)
					errorMessage = "JSON Validation Error.";
				dispatchEvent( new Event( UNSUCCESSFUL_READ_REPORT));
			}
		}
		
		/**
		* Validates a JSON object, to confirm that it contains all the necessary
		* details of a WeatherReport. 
		*
		* @param jsonReport A JSON object representing the Weather Report
		* @return True if the report contained all necessary data. False otherwise
		*/
		private function validateJsonReport( jsonReport:Object):Boolean
		{
			if( !jsonReport.hasOwnProperty("date"))
			   return false;
			
			// If the report is successful, validate it's data
			if( jsonReport.hasOwnProperty("successful") && jsonReport.successful == true)
			{
				// First, ensure that the current conditions are valid.
				if( !jsonReport.hasOwnProperty("location") ||
				   !jsonReport.hasOwnProperty("current_condition") ||
				   !jsonReport.hasOwnProperty("current_temp") ||
				   !jsonReport.hasOwnProperty("icon"))
				   {
					   return false;
				   }
				
				// Then, ensure the three-day forecast is valid
				if( !jsonReport.hasOwnProperty("forecast"))
					return false;
				
				for( var i:int = 0; i < WeatherReport.NUM_DAYS; i++){
					if( !jsonReport.forecast[i])
						return false;
						
					if( !jsonReport.forecast[i].hasOwnProperty("day_name") ||
					   !jsonReport.forecast[i].hasOwnProperty("condition") ||
					   !jsonReport.forecast[i].hasOwnProperty( "high") ||
					   !jsonReport.forecast[i].hasOwnProperty( "low") ||
					   !jsonReport.forecast[i].hasOwnProperty( "icon"))
					   {
						   return false;
					   }
				}
				return true;
			} else {
				// Unsuccessful reports are automatically returned false
				if( errorMessage == null)
					errorMessage = "Failed Internet Connection.";
				return false;
			}
		}
		
		/**
		* Parses & writes the JSON Report to memory. 
		* Before calling this method, be sure to validate the report.
		* @see validateJsonReport
		*/
		private function parseJsonReport( jsonReport:Object):void
		{
			trace("Parsing json...");
			weatherReport = new WeatherReport();
			
			// Write the current conditions to memory
			weatherReport.date = jsonReport.date;
			weatherReport.location = jsonReport.location;
			weatherReport.current_condition = jsonReport.current_condition;
			weatherReport.current_temp = jsonReport.current_temp;
			weatherReport.icon = jsonReport.icon;
			
			// Write the forecast to memory
			for( var i:int = 0; i < WeatherReport.NUM_DAYS; i++){
				weatherReport.getForecast(i).day_name = jsonReport.forecast[i].day_name;
				weatherReport.getForecast(i).condition = jsonReport.forecast[i].conditions;
				weatherReport.getForecast(i).high = jsonReport.forecast[i].high;
				weatherReport.getForecast(i).low = jsonReport.forecast[i].low;
				weatherReport.getForecast(i).icon = jsonReport.forecast[i].icon;
			}
		}
		
		// --- REPORT DISPLAY --------------------------------------------
		
		/**
		* Displays the WeatherReport onscreen.
		*/
		private function displayReport( e:Event = null):void
		{
			trace("Displaying report...");
			errorMessage = null; // reset the error message.
			
			clock.setLocation( weatherReport.location);
			conditions.setCondition( weatherReport.current_condition, weatherReport.current_temp, weatherReport.getForecast(0).low, weatherReport.icon);
			forecast1.setForecast( weatherReport.getForecast(1).day_name, weatherReport.getForecast(1).high, weatherReport.getForecast(1).low, weatherReport.getForecast(1).icon);
			forecast2.setForecast( weatherReport.getForecast(2).day_name, weatherReport.getForecast(2).high, weatherReport.getForecast(2).low, weatherReport.getForecast(2).icon);
			forecast3.setForecast( weatherReport.getForecast(3).day_name, weatherReport.getForecast(3).high, weatherReport.getForecast(3).low, weatherReport.getForecast(3).icon);
			
			timeStamp.text = REPORT_FOOTER + weatherReport.date;
		}
		
		public function enterErrorMode( e:Event = null):void
		{
			trace("Entering error mode!");
			
			if( errorMessage == null)
				errorMessage = "Unknown error.";
				
			clock.enterErrorMode(e);
			conditions.enterErrorMode(e);
			conditions.setError( errorMessage);
			forecast1.enterErrorMode(e);
			forecast2.enterErrorMode(e);
			forecast3.enterErrorMode(e);
			
			timeStamp.text = "";
		}
		
		/**
		* This is the mode the program starts up in
		*/
		public function enterSafeMode( e:Event = null):void
		{
			trace("Entering safe mode");
			
			clock.enterSafeMode(e);
			conditions.enterSafeMode(e);
			forecast1.enterSafeMode(e);
			forecast2.enterSafeMode(e);
			forecast3.enterSafeMode(e);
			
			timeStamp.text = "";
		}
		
		// --- ERROR HANDLING --------------------------------------------
		/**
		* Handle any IO Exceptions that may occur.
		*/
		private function handleIOError( ...params):void
		{
			trace( "**There was an error loading the report file.");
			trace( "**Please ensure that the file exists and is valid.");
			trace( "** " + this.report_location);
			
			if( errorMessage == null)
				errorMessage = "File I/O Error.";
				
			enterErrorMode();
		}
	}
}