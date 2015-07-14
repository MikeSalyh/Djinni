using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Timers;

namespace Djinni
{
    /// <summary>
    /// This class is a recurring event that gathers weather data.
    /// </summary>
    class WeatherTask
    {
        #region Variables

        // How ofteh tne WeatherReports are generated. 20 minutes in miliseconds.
        public const int INTERVAL = 2000; //1200000;

        // The key to access the WU API
        private String api_key;

        #endregion

        #region Constructor & Run Method
        
        /// <summary>
        /// A WeatherTask object. When run, the WeatherReport object connects to the WeatherUnderground API and gathers data.
        /// Then, it outputs that information into the console.
        /// </summary>
        /// <param name="api_key">The key to access the WeatherUnderground API</param>
        public WeatherTask( String api_key)
        {
            this.api_key = api_key;
        }

        /// <summary>
        /// Generates a WeatherReport and outputs it into the console.
        /// Use this overload if triggering from a timer.
        /// </summary>
        /// <param name="source">The source of the event that triggered this function.</param>
        /// <param name="e">The timer event object.</param>
        public void run(Object source, ElapsedEventArgs e)
        {
            run();
        }

        /// <summary>
        /// Generates a WeatherReport and outputs it into the console.
        /// </summary>
        public void run()
        {
            printWeatherReport(new WeatherReport(api_key));
        }
        
        #endregion

        #region Print Methods

        /// <summary>
        /// Logs a weather report into the console if it was successfully generated.
        /// Logs error data into the console if  the report was unsuccesfully generated.
        /// </summary>
        /// <param name="weather_report"></param>
        private void printWeatherReport(WeatherReport weather_report)
        {
            // TODO: Write this method.
            Console.WriteLine("Printing weather report...");
        }

        #endregion

    }
}
