using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Timers;

namespace Djinni
{
    class WeatherMan
    {

        #region Djinni - Top Level
        // *********************************************
        //  TOP LEVEL DJINNI
        // *********************************************

        // The version number of this application
        public const String VERSION = "0.4.0";

        // The root folder of the Djinni Home AI System
        public const String ROOT_FILE_PATH = "C:\\ProgramData\\Djinni\\";
        #endregion

        #region Weatherman
        // *********************************************
        //  WEATHERMAN APP
        // *********************************************

        // The filename of the Weather Underground API Key.
        public const String WEATHER_UNDERGROUND_KEY_NAME = "wu_key.txt";

        // Key to the WU API. It read from a textfile.
        private static String api_key;

        // An async timer that gathers the weather data.
        private static Timer aTimer;

        private static WeatherTask weather_task;


        /// <summary>
        /// Main class of the Weatherman App.
        /// </summary>
        /// <param name="args"></param>
        static void Main(string[] args)
        {
            System.Console.WriteLine("*** WEATHERMAN APP " + VERSION + " ***");
            System.Console.WriteLine("");

            
            try
            {
                // Get the API key from the Djinni Program Data folder
                api_key = new APIKeyReader(WEATHER_UNDERGROUND_KEY_NAME).getKey();

                // Set a recurring task to gather weather data on a fixed interval
                weather_task = new WeatherTask( api_key);
                weather_task.run(); // immediately run the weather task.

                // Create a timer with a two second interval.
                aTimer = new System.Timers.Timer(WeatherTask.INTERVAL);

                // Hook up the Elapsed event for the timer. 
                aTimer.Elapsed += weather_task.run;
                aTimer.Enabled = true;
            }
            catch (Exception e)
            {
                // If the API key cannot be read, log the error in the console.
                // Note, that a URL connection is never attempted.
                Console.WriteLine("Error: WeatherMan could not locate your API key");
                Console.WriteLine(e.Message);
                Console.WriteLine("The API Key should be stored in " + ROOT_FILE_PATH + APIKeyReader.KEY_FOLDER + WEATHER_UNDERGROUND_KEY_NAME);
            }


            // Termination code:
            Console.WriteLine("");
            Console.WriteLine("Press the Enter key to exit the program... ");
            Console.ReadLine();
            Console.WriteLine("Terminating the application...");
        }

        #endregion
    }
}
