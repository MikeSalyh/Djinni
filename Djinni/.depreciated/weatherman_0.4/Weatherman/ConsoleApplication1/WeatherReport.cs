using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace Djinni.Apps.WeatherManApp
{
     /// <summary>
     /// This class gathers and holds weather data.
     /// All information comes from the WeatherUnderground API.
     /// </summary>
    class WeatherReport
    {

        #region Consts & Vars

        // --- URL CONSTANTS ------------------------

        // These strings, combined with an API_KEY for an API request.
        // WeatherMan uses two types of calls to gather its data: CONDITIONS and FORECAST.
        // For full documentation, visit http://www.wunderground.com/weather/api/d/docs
        private const String API_REQUEST_HEAD = "http://api.wunderground.com/api/";
        private const String API_REQUEST_TAIL = "/q/autoip.json";
        private const String CONDITIONS = "/conditions";
        private const String FORECAST = "/forecast";

        // --- DATE VARIABLES ------------------------

        // The date this WeatherReport was generated
        private DateTime date;

        // --- CONNECTION VARIABLES ------------------

        // The WU API key of the user
        private String api_key;

        // TRUE if this object has successfully connected to the WU conditions report. False by default.
        private bool successful_connection_conditions = false;

        // TRUE if this object has successfully connected to the WU three-day forecast. False by default.
        private bool successful_connection_forecast = false;

        // If there is an issue connecting to, or reading from the API, this string holds that error message.
        private String error_message;

        // --- DATA VARIABLES -------------------------

        // The location of the user's IP address.
        private String location;

        // The current weather conditions ("Mostly Clear", "Rainy", etc.) at the user's location
        private String current_condition;

        // The current temperature as a String, measured in Fahrenheit.
        private String current_temp;

        // An array that holds the three day forecast. It has a length of four positions, because today's data is held in position [0].
        private DayForecast[] forecast = new DayForecast[4];

        // The current condition icon, displayed by the UI.
        private String current_icon;


        #endregion

        #region Constructor
        /// <summary>
        /// This class gathers and holds weather data. All information comes from the WeatherUnderground API, using the location of the user's IP address.
        /// <para/>Creating a report makes 2 calls to the WU API. The API can handle a max of 10 calls/min, and 500 calls/day. 
        /// </summary>
        /// <param name="api_key">A key to the WU API</param>
        /// <exception cref="System.IO.IOException"></exception>
        public WeatherReport( String api_key)
        {
            this.api_key = api_key;
            this.date = DateTime.Now;

            // Connect to the API and gather data.
            // The gathered data is saved into this object.

            // Two calls are made to the API. The first gathers today's conditions.
            successful_connection_conditions = gatherDataFromConditionsReport();

            //The second gathers the three-day forecast.
            successful_connection_forecast = gatherDataFromForecastReport();
        }
        #endregion

        #region Getters

        /// <summary>
        /// Returns whether this report was generated successfully.
        /// Before probing a report for info, always make sure it was successful. 
        /// Unsuccessful reports do not throw exceptions.
        /// </summary>
        /// <returns>True if the report was successful. False otherwise.</returns>
        public bool wasSuccessful() {
            return successful_connection_forecast && successful_connection_conditions;
        }

        /// <summary>
        /// The date and time the report was generated.
        /// </summary>
        public DateTime getDate()
        {
            return date;
        }

        /// <summary>
        /// If there was a connection error, this string holds it. Otherwise, null.
        /// </summary>
        public String getErrorMessage()
        {
            return error_message;
        }

        /// <summary>
        /// The location of the user's IP address.
        /// </summary>
        public String getLocation()
        {
            return location;
        }

        /// <summary>
        /// The current weather conditions. For example, "cloudy" or "mostly sunny"
        /// </summary>
        public String getCurrentConditions()
        {
            return current_condition;
        }

        /// <summary>
        /// The current temperature as a string, measured in Fahrenheit.
        /// </summary>
        public String getCurrentTemp()
        {
            return current_temp;
        }

        /// <summary>
        /// The current condition icon, to be displayed by the UI.
        /// </summary>
        /// <returns></returns>
        public String getCurrentIcon()
        {
            return current_icon;
        }

        /// <summary>
        /// Get the forecast for a future day.
        /// </summary>
        /// <param name="day">Between 0 (today) and 3 (three days from today)</param>
        /// <returns>A DayForecast object for the requested day.</returns>
        /// <exception cref="System.ArgumentOutOfRangeException">If the day isn't between 0 and 3.</exception>
        public DayForecast getForecast( int day)
        {
            if (day < 0 || day >= forecast.Length)
                throw new ArgumentOutOfRangeException(String.Format("Requested forecast is out of range. Valid Range [0 - {0}]. You said {1}.", forecast.Length - 1, day));
            return forecast[day];
        }

        #endregion

        #region Data Collection Methods


        /// <summary>
        /// Gathers data from WU Conditions report, and saves it into this object.
        /// Sets the following variables: user_city, current_condition, current_temp
        /// </summary>
        /// <returns>True if successful</returns>
        private bool gatherDataFromConditionsReport()
        {
            try
            {
                // connect to API and grab data as a JSON object
                // write that data into my variables
                dynamic obj = connectToAPI(CONDITIONS);
                location = obj.current_observation.display_location.full;
                current_condition = obj.current_observation.weather;
                current_temp = obj.current_observation.temp_f;
                current_icon = obj.current_observation.icon;
                return true;
            }
            catch (Exception e)
            {
                error_message = e.Message;
                return false;
            }
        }

        /// <summary>
        /// Gathers data from the WU Forecast Report, and saves it into this object.
        /// Sets the following variables: forecast
        /// </summary>
        /// <returns>True if successful</returns>
        private bool gatherDataFromForecastReport()
        {
            try
            {
                // connect to API and grab JSON data
                // use a loop to write that into the forecast
                dynamic obj = connectToAPI(FORECAST);

                JArray results = obj.forecast.simpleforecast.forecastday;
                foreach( dynamic result in results)
                {
                    if( result.period != null)
                    {
                        // The period represents how many days the forecast is from the current day, with 1 being today.
                        int period = result.period;
                        
                        // Since our forecast array is zero-based, we must subtract 1 from the period.
                        period -= 1;

                        // Before adding a DayForecast object into the array, ensure it will fit into the array:
                        if( period >= 0 && period < forecast.Length)
                        {
                            String day_name = result.date.weekday_short;
                            String condition = result.conditions;
                            String high = result.high.fahrenheit;
                            String low = result.low.fahrenheit;
                            String icon = result.icon;

                            forecast[period] = new DayForecast(day_name, condition, high, low, icon);
                        }
                    }

                }
                return true;
            }
            catch (Exception e)
            {
                error_message = e.Message;
                return false;
            }
        }

        /// <summary>
        /// Connects to the WeatherUnderground API, and returns a JSON object containing the requested weather report.
        /// </summary>
        /// <param name="report_type">The tye of report. Use CONDITIONS or FORECAST. Anything else will throw an exception</param>
        /// <returns>A JSON object containing the requested information</returns>
        /// <exception cref="System.ArgumentException ">Throws an exception if the param is invalid</exception>
        private dynamic connectToAPI(String report_type)
        {
            // First, ensure that the parameter is a valid type of report:
            switch (report_type)
            {
                case CONDITIONS:
                case FORECAST:
                    break;
                default:
                    error_message = String.Format("Illegal arguement: {0}. Did not attempt API connection.", report_type);
                    throw new ArgumentException(error_message);
            }

            using (var webClient = new System.Net.WebClient())
            {
                var URL = API_REQUEST_HEAD + api_key + report_type + API_REQUEST_TAIL;
                var json = webClient.DownloadString(URL);
                return JsonConvert.DeserializeObject(json);
            }
        }

        #endregion


        #region Print Methods

        /// <summary>
        /// Prints out the contents of this weather report to the console.
        /// </summary>
        public void print()
        {
            if( wasSuccessful())
            {
                // If the weather report was successful, log it into the console
                Console.WriteLine(getLocation());
                Console.WriteLine(getCurrentConditions() + ". " + getCurrentTemp());
                Console.WriteLine("");
                for( var i = 0; i < forecast.Length; i++)
                {
                    Console.WriteLine(forecast[i].toString());
                }
            }
            else
            {
                // If there was an error generating the report, log it into the console.
                // This is not exceptional behavior.
                Console.WriteLine("Error generating Weather Report.");
                Console.WriteLine(getErrorMessage());
            }

            Console.WriteLine("");
            Console.WriteLine("Timestamp: " + getDate());

            // Add buffer between reports
            Console.WriteLine("************************************");
        }

        /// <summary>
        /// Converts the WeatherReport into a speakable string, and returns it.
        /// </summary>
        /// <returns>A version of the report that can be easily spoken in English.</returns>
        override public String ToString()
        {
            if( wasSuccessful())
            {
                // If the weather report was successful, speak it.
                return String.Format("The weather in {0} is {1} degrees Fahrenheit, {2}. Today's high will be {3} degrees. Today's low will be {4} degrees.",
                    getLocation(), getCurrentTemp(), getCurrentConditions(), getForecast(0).getHigh(), getForecast(0).getLow());
            }
            else
            {
                // If there was an error generating the report, speak it.
                return "There was an error generating this WeatherReport. Check the console for details.";
            }
        }
        
        #endregion

    }
}
