package weatherman {
	import flash.display.MovieClip;
	import flash.events.Event;
	
	/**
	* This movieclip contains today's weather information.
	* @author Mike
	*/
	
	/* MovieClip Structure:
	*	this
	*	|
	*	|- high (TextField)
	*	|- low (TextField)
	*	|- condition (TextField)
	*	|- icon (MovieClip)
	*/
	
	public class ConditionMC extends MovieClip implements IWeatherDisplay{
		
		// --- VARS & CONSTS -------------------------------------
		
		// This is added before the low temp when it's displayed
		private const LOW_BUFFER:String = "/ ";
		
		// --- METHODS -------------------------------------------
		public function ConditionMC() {
			
			// when the program starts, no report will be loaded.
			// so, start in safe mode.
			enterSafeMode();
		}
		
		/**
		* If a bad report comes down the pipe, enter Error Mode.
		* For this object, that means hiding the icons & temps
		* and displaying the error message in the conditions field
		*/
		public function enterErrorMode(e:Event = null):void
		{
			icon.gotoAndStop("error");
			high.text = "";
			low.text = "WeatherMan has encountered an error:";
			condition.text = "";
		}
		
		/** displays a string in the conditions box */
		public function setError( errorString:String):void
		{
			condition.text = errorString;
		}
		
		/**
		* When the program starts, there will be
		* a short time before the most recent weather
		* report is fetched. Safe mode is like badReport mode
		* but without any warning messages.
		*/
		public function enterSafeMode(e:Event = null):void
		{
			icon.gotoAndStop("error");
			high.text = "";
			low.text = "";
			condition.text = "Loading...";
		}
		
		/**
		* Set the current conditions.
		* @param conditionString The main display string ("Clear", "Partly Cloudy", etc.)
		* @param high/low The high and low temp
		* @param icon which icon to display
		*/
		public function setCondition( conditionString:String, high:String, low:String, icon:String):void
		{
			this.condition.text = conditionString;
			
			// The high comes as a float. Change it to an int.
			var highInt:int = int(high);
			
			this.high.text = highInt + "°";
			this.low.text = LOW_BUFFER + low + "°";
			
			// Set the icon, according to whether or not it's day or night
			var d:Date = new Date();
			var isNight:Boolean = d.hours > 18 || d.hours < 6;
			if( isNight)
				this.icon.gotoAndStop( "nt_" + icon);
			else
				this.icon.gotoAndStop( icon);
		}

	}
	
}
