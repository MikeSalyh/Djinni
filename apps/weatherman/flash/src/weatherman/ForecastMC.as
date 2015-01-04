package weatherman {
	import flash.display.MovieClip;
	import flash.events.Event;
	
	/** 
	* This class represents a single day's forecast.
	* @author Mike
	*/
	
	/* MovieClip Structure:
	*	this
	*	|
	*	|- dayName (MovieClip)
	*	|- high_temp (TextField)
	*	|- high_icon (MovieClip)
	*	|- low_temp (TextField)
	*	|- low_icon (MovieClip)
	*/
	
	public class ForecastMC extends MovieClip implements IWeatherDisplay{

		public function ForecastMC() {
			enterSafeMode();
		}
		
		/**
		* If a bad report comes down the pipe, enter Error Mode.
		* For this object, that means hiding the day names, and the
		* high / low temps
		*/
		public function enterErrorMode(e:Event = null):void
		{
			dayName.gotoAndStop("error");
			high_temp.text = "";
			low_temp.text = "";
			high_icon.gotoAndStop("error");
			low_icon.gotoAndStop("error");
		}
		
		/**
		* When the program starts, there will be
		* a short time before the most recent weather
		* report is fetched. Safe mode is like badReport mode
		* but without any warning messages.
		*/
		public function enterSafeMode(e:Event = null):void
		{
			// The Forecasts have the same error & safe mode
			enterErrorMode(e);
		}
		
		/** Sets a day's forecast
		* @param day_name the three-day day name (Mon, Tue, etc.)
		* @param high/low the daily high and low temp
		* @icon which icon to display
		*/
		public function setForecast( day_name:String, high:String, low:String, icon:String):void
		{
			dayName.gotoAndStop(day_name);
			high_temp.text = high;
			low_temp.text = low;
			high_icon.gotoAndStop( icon);
			low_icon.gotoAndStop( "nt_" + icon);
		}

	}
	
}
