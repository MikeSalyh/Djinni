using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Djinni
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
        /// This class gathers and holds weather data.
        /// All information comes from the WeatherUnderground API, using the location of the user's IP address.
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
                return true;
            }
            catch (Exception e)
            {
                error_message = "Could not connect to server." + e.Message;
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
                return true;
            }
            catch (Exception e)
            {
                error_message = "Could not connect to server." + e.Message;
                return false;
            }
        }

        #endregion

    }
}
