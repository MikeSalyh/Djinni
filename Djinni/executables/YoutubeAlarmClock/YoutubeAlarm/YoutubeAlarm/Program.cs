using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace YoutubeAlarm
{
    /// <summary>
    /// This program is a standalone executable.
    /// When launched, it opens a YouTube video from a series of choices at random.
    /// This program doesn't integrate into the larger Djinni framework. It's purely ad hoc. 
    /// </summary>
    class Program
    {
        #region Consts & Vars
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
        /// The folder where data from this application is stored.
        /// </summary>
        private const String APP_DATA_FOLDER = "app_data\\youtubealarm\\";
        #endregion

        #region Program vars + consts

        /// <summary>
        /// The file containing the playlist data.
        /// </summary>
        private const String PLAYLIST_FILE = "playlist.txt";

        //private const String AUTOPLAY_APPEND = "&autoplay=0";

        #endregion
        #endregion


        static void Main(string[] args)
        {
            PlaylistReader r = new PlaylistReader(Path.Combine(ROOT_FILE_PATH, APP_DATA_FOLDER), PLAYLIST_FILE);
            r.shuffleURLs();
            Console.WriteLine(r.getFirstID());
            
            YoutubeHTMLBuilder y = new YoutubeHTMLBuilder(Path.Combine(ROOT_FILE_PATH, APP_DATA_FOLDER), r.getFirstID());
            y.writeHTML();

            System.Diagnostics.Process.Start(y.getHTML());

            System.Console.WriteLine("Press any key to terminate...");
            System.Console.ReadKey();
        }
    }
}
