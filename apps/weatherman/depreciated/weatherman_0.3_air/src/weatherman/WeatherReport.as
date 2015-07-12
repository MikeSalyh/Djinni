package weatherman{
	/** 
	* This object holds the JSON data in a non-dynamic,
	* strictly-typed form.
	*
	* @author Mike
	*/
	public class WeatherReport {
		
		/** 
		* How many days the forecast is for, counting the
		* current day. 
		*/
		public static const NUM_DAYS:int = 4;
		
		// The current conditions
		public var date:String;
		public var location:String;
		public var current_condition:String;
		public var current_temp:String;
		public var icon:String;
		
		// weather forecasts
		private var forecast:Vector.<DayForecast>;
		
		public function WeatherReport()
		{
			forecast = new Vector.<DayForecast>();
			for( var i:int = 0; i < NUM_DAYS; i++)
				forecast.push( new DayForecast());
		}
		
		/**
		* @param dayNumber The day of the forecast you want to get. Today is day 0. Tomorrow is day 1, and so on.
		* @return The specified day forecast.
		* @throws Error if the dayNumber provided is invalid.
		*/
		public function getForecast( dayNumber:int):DayForecast
		{
			// Ensure that the forecast day exists
			if( dayNumber < 0 || dayNumber >= NUM_DAYS)
			{
				throw new Error("Invalid day forecast requested. Please use a number between 0 and " + (NUM_DAYS - 1) + ". You used " + dayNumber);
			}
			return forecast[dayNumber];
		}
	}
	
}
