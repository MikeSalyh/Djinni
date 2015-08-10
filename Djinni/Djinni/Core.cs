using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Djinni.Apps.WeatherManApp;

using Module.Vox;

namespace Djinni
{
    class Core
    {
        #region Variables & Consts

        #region System Consts
        /// <summary>
        ///  The version number of this application
        /// </summary>
        public const String VERSION = "0.0.1";

        /// <summary>
        ///  The root folder of the Djinni Home AI System
        /// </summary>
        private const String ROOT_FILE_PATH = "C:\\ProgramData\\Djinni\\";

        /// <summary>
        /// The folder which contains all API keys.
        /// </summary>
        private const String KEY_FILE_PATH = ROOT_FILE_PATH + "Keys\\";
        #endregion

        #region Modules
        public static Vox Mod_Vox = new Vox();

        #endregion

        #region Apps
        /// <summary>
        /// The Weatherman app
        /// </summary>
        public static WeatherMan App_Weatherman;

        // The filename of the Weather Underground API Key.
        private const String WEATHER_UNDERGROUND_KEY_NAME = "wu_key.txt";


        #endregion

        #region UserData

        /// <summary>
        /// My name.
        /// </summary>
        // TODO: This should be loaded from a file.
        const String MY_NAME = "Mike";
        #endregion

        #endregion


        /// <summary>
        /// The main executable of the Djinni Core.
        /// </summary>
        static void Main(string[] args)
        {
            System.Console.WriteLine("*** DJINNI HOME AI " + VERSION + " ***");
            System.Console.WriteLine("");


            //Initiate all apps.
            try
            {
                System.Console.WriteLine("Initiating Apps...");

                App_Weatherman = new WeatherMan(new APIKeyReader(KEY_FILE_PATH, WEATHER_UNDERGROUND_KEY_NAME).getKey());

                // All aps have successfully initiated.
                System.Console.WriteLine("All apps initiated.");
                System.Console.WriteLine("");
                System.Console.WriteLine("");
            }
            catch (Exception e)
            {
                System.Console.WriteLine();
                System.Console.WriteLine();
                System.Console.WriteLine(e.Message);
                System.Console.WriteLine("All apps could not be initiated. Program will now terminate.");
                System.Console.WriteLine("Press RETURN to continue.");
                System.Console.ReadLine();
                return;
            }


            // Core Actions.
            
            // This is a temporary wake up code. It should be removed before v.0.1 
            Mod_Vox.Queue(String.Format("Good morning {0}. It's {1}. You should wake up.", MY_NAME, getTimeVocalized()));

            // Read a Weather Report
            App_Weatherman.CreateWeatherReport(Mod_Vox);


            // Terminator
            System.Console.WriteLine();
            System.Console.WriteLine("********");
            System.Console.WriteLine("Djinni core is running stably.");
            System.Console.WriteLine("Press ENTER to terminate.");
            System.Console.ReadLine();
        }

        /// <summary>
        /// This method is TEMP. It should be part of the TemporalLobe module. Remove before v0.1
        /// <para/> This method returns the time in HH:MM format, human-spoken.
        /// </summary>
        /// <returns></returns>
        private static String getTimeVocalized()
        {
            String ampm = DateTime.Now.Hour > 12 ? "PM" : "AM";
            return String.Format("{0} {1} {2}", (DateTime.Now.Hour % 12), DateTime.Now.Minute, ampm);
        }
    }
}
