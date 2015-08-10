package weatherman {
	import flash.display.MovieClip;
	import flash.utils.Timer;
	import flash.events.TimerEvent;
	import flash.events.Event;
	import flash.text.TextField;

	/**
	* This is the movieclip at the top-right of the screen. It contains
	* the  time, date, and location 
	*
	* @author: Mike
	*/
	
	/* MovieClip Structure:
	*	this
	*	|
	*	|- time (TextField)
	*	|- AMPM (TextField)
	*	|- colon (MovieClip)
	*	|- dateAndLocation (TextField)
	*/
	
	public class ClockMC extends MovieClip implements IWeatherDisplay{
		
		// --- VARS & CONSTS -------------------------------------
		/** This string seperates the date & location */
		private const DATE_AND_LOCATION_SEPERATOR:String = "   |   ";
		
		/** A timer that manages the colon's flashing on screen 
		* The timer goes off every second, indefinitely.
		*/
		private var timer:Timer = new Timer(1000, 0);
		
		/** The user's location (City, State). */
		private var location:String = "";
		
		
		// --- METHODS -------------------------------------------------
		/** The clock movieclip. It displays the time, date, and location */
		public function ClockMC() {
			
			// the colon on the timer-clock should flash on and off every second
			timer.addEventListener(TimerEvent.TIMER, tick);
			timer.start();
			
			// when the program starts, no report will be loaded.
			// so, start in safe mode.
			enterSafeMode();
		}
		
		/**
		* If a bad report comes down the pipe, enter Error Mode.
		* In terms of the clock, that means hiding the location.
		*/
		public function enterErrorMode(e:Event = null):void
		{
			// Reset the location to unknown.
			location = "";
			
			// Update the time, so the previous location is set to the new
			// blank location.
			updateTime();
			
			// Make the colon visible
			colon.visible = true;
		}
		
		/**
		* When the program starts, there will be
		* a short time before the most recent weather
		* report is fetched. Safe mode is like badReport mode
		* but without any warning messages.
		*/
		public function enterSafeMode(e:Event = null):void
		{
			// In the clock's case, safeMode is exactly like
			// badReport mode.
			enterErrorMode();
		}
		
		/** Set the clock's location city. 
		* This is displayed in the dateAndTime textfield. 
		* @param location the user's location city
		*/
		public function setLocation( location:String):void
		{
			// add the seperator to the location before writting it into memory.
			this.location = DATE_AND_LOCATION_SEPERATOR + location;
			updateTime();
		}
		
		
		// ---- PRIVATE METHODS -------------
		
		
		/**
		* Tick the clock. Called once per second. 
		*/
		private function tick( e:Event = null):void 
		{
			flashColon();
			updateTime();
		}
		
		/**
		* Updates the HH:MM time. When this method is called, the 
		* time textfield, and the AMPM textfield are synced with the system clock.
		* The date section of dateAndLocation is also updated.
		*/
		private function updateTime():void
		{
			// create a date object
			var d:Date = new Date();
			
			// First, gather the time, in HH:MM format
			
			// Date objects save the hour-time in military time.
			//Change from military time to 12-hour time:
			var h:int = d.hours % 12;
			if( h == 0)
				h = 12; // The mathimatical hour of '0' is really '12'
			var m:int = d.minutes;
			
			// If minutes are less than 10, we'll need to add an extra zero to the time display
			var minBuffer:String = getBuffer(m);
			
			// Combine all the strings to get the final time.
			// Note that there's NO COLON in this string! The colon is a seperate MC.
			var time_out:String = h + " " + minBuffer + m;
			
			// Set the time textfield to this newly gathered string:
			this.time.text = time_out;
			
			// if the hours are greater than 12, it's PM. Otherwise, it's AM.
			var ampm_out:String = d.hours >= 12 ? "PM" : "AM";
			this.AMPM.text = ampm_out;
			
			// Then, update the date. The location is in the same string,
			// so it's set again too. 
			//Note that Date.month is base 0, so 1 must be added to it.
			var date_out:String = getBuffer(d.month+1) + (d.month+1) + "/" + getBuffer(d.date) + d.date + "/" + d.fullYear;  
			this.dateAndLocation.text = date_out + location;
		}
		
		
		/**
		* Pass this method a number, and if that number is less than
		* ten, returns "0". Otherwise, returns an empty string. This
		* method is useful for clocks and dates.
		*/
		private function getBuffer( num:Number):String
		{
			return num < 10 ? "0" : "";
		}
		
		
		/** Toggles the visibility of the colon.
		* @return: Whether or not the colon visible after this method completes 
		*/
		private function flashColon( e:Event = null):Boolean
		{
			this.colon.visible = !this.colon.visible;
			return this.colon.visible;
		}

	}
	
}
