using System;
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
        /// </summary>
        /// <param name="vox">Module in charge of voice out.</param>
        /// <param name="API_key_filepath">The folder which contains the WeatherUnderground API key.</param>
        /// <param name="API_key_filename">The name of the non-encrypted text file which contains the WeatherUnderground API key.</param>
        public WeatherMan(String API_key_filepath, String API_key_filename)
        {
            System.Console.WriteLine("Loading App: WeatherMan v{0}", VERSION);
            System.Console.WriteLine("    Reading API Key...");

            try
            {
                // Get the API key from the Djinni Program Data folder
                api_key = new APIKeyReader(API_key_filepath, API_key_filename).getKey();
                System.Console.WriteLine("    API key found.");
                System.Console.WriteLine("    App loaded successfully.");
            }
            catch (Exception e)
            {
                // If the API key cannot be read, log the error in the console and throws an error.
                // Note, that a URL connection is never attempted.
                String errorMessage = "WeatherMan could not locate your API key.";
                Console.WriteLine("    Error: {0}", errorMessage);
                Console.WriteLine("    {0}", e.Message);
                System.Console.WriteLine("    App could not be loaded.");
                throw new IOException(errorMessage);
            }
        }

        /// <summary>
        /// Create a Weatherman app. This app is capable of gathering weather data, and speaking it to the user.
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
