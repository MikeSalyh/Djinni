﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

using Module.Vox;

namespace Djinni.Apps.WeatherManApp
{
    class WeatherMan
    {
        #region Vars & Consts 
        /// <summary>
        /// The version of this app.
        /// </summary>
        public const String VERSION = "0.4.1";    

        /// <summary>
        ///  Key to the WU API.
        /// </summary>
        private String api_key;

        #endregion

        #region Constructor
        /// <summary>
        /// Create a Weatherman app. This app is capable of gathering weather data, and speaking it to the user.
        /// <para/> The constructor does not make any calls to the WU API by itself.
        /// </summary>
        /// <param name="vox">Module in charge of voice out.</param>
        /// <param name="API_key">String of the WeatherUnderground API key.</param>
        public WeatherMan(String API_key)
        {
            System.Console.WriteLine("Loading App: WeatherMan v{0}", VERSION);
            api_key = API_key;
            System.Console.WriteLine("    App loaded successfully.");
        }
        #endregion

        #region Public Methods
        /// <summary>
        /// Creates a WeatherReport in the console, by pinging the WeatherUnderground API.
        /// <para/>This method makes 2 calls to the WU API. The API can handle up to 10 calls/min, and 500 calls/day.
        /// </summary>
        public void CreateWeatherReport()
        {
            WeatherReport myReport = new WeatherReport(api_key);
            myReport.print();
        }

        /// <summary>
        /// Creates a WeatherReport, spoken out loud, and logged in the console, by pinging the WeatherUnderground API.
        /// <para/>This method makes 2 calls to the WU API. The API can handle up to 10 calls/min, and 500 calls/day.
        /// </summary>
        /// <param name="vox">A Vox module, that will speak the report.</param>
        public void CreateWeatherReport(Vox vox)
        {
            WeatherReport myReport = new WeatherReport(api_key);
            
            // print the report into the console.
            myReport.print();

            // speak the line over the Vox.
            vox.Queue(myReport.ToString());
        }
        #endregion
    }
}
