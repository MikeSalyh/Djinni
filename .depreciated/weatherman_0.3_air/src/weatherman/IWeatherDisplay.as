package weatherman {
	import flash.events.Event;

	/**
	* MovieClips that recieve date from the JSON weather report
	* @author: Mike
	*/
	public interface IWeatherDisplay {

		// Interface methods:
		
		// every IWeatherDisplay needs to be capable of handling a bad WeatherUnderground report.
		// note that the bad reports still have proper JSON formatting.
		function enterErrorMode(e:Event = null):void;
		
		// like ErrorMode, but without a warning message. This is the view that the program
		// opens with
		function enterSafeMode(e:Event = null):void;

	}
	
}
