using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace YoutubeAlarm
{
    class PlaylistReader
    {

#region Conts & Vars
        // The absolute path of the playlist text file. Includes the root filepath.
        private String path;

        // The playlists are held here.
        private List<String> playlists;

#endregion

#region Methods
        /// <summary>
        /// This class reads a series of URLs from a text file.
        /// <para/>The text file must contain a series of URLS seperated by carrige returns.
        /// <para/>The list is best generated using youParse.py
        /// </summary>
        /// <param name="file_path">The full path to the files directory. For example, "C:\Foo\"</param>
        /// <param name="file_name">The name of the file to read, including its extension. For example, "Bar.txt"</param>
        public PlaylistReader( String file_path, String file_name)
        {
           path = file_path + file_name;
        }

        /// <summary>
        /// This class reads a series of URLs from a text file.
        /// <para/>The text file must contain a series of URLS seperated by carrige returns.
        /// <para/>The list is best generated using youParse.py
        /// </summary>
        /// <param name="file_path_and_name">The full path and name of the file to read. For example, "C:\Foo\Bar.txt"</param>
        public PlaylistReader( String file_path_and_name)
        {
            path = file_path_and_name;
        }


        /// <summary>
        /// Returns an array of URLS.
        /// </summary>
        /// <returns>A list of URLS</returns>
        /// <exception cref="System.IO.IOException">Thrown when the file cannot be read.</exception>
        /// <exception cref="System.OutOfMemoryException">Thrown when there is insufficient memory to allocate a buffer for the returned string.</exception>
        public List<String> getURLs()
        {
            return playlists == null ? playlists = readPlaylistFromFile(path) : playlists;
        }

        /// <summary>
        /// Reads the URLS in the text file and returns them as a String array.
        /// </summary>
        /// <param name="filepath">The absolute filepath to the text file containing the URLs</param>
        /// <returns>A list of URL strings</returns>
        /// <exception cref="System.IO.IOException">Thrown when the file cannot be read.</exception>
        /// <exception cref="System.OutOfMemoryException">Thrown when there is insufficient memory to allocate a buffer for the returned string.</exception>
        private  List<String> readPlaylistFromFile( String filepath)
        {
            List<String> output = new List<String>();

            String line;

            try
            {
                using (StreamReader stream_reader = new StreamReader(filepath))
                {
                    while ((line = stream_reader.ReadLine()) != null)
                    {
                        output.Add(line);
                    }
                }
                return output;
            }
            catch (IOException)
            {
                System.Console.WriteLine("Could not read file.");
                return output;
            }
        }

        /// <summary>
        /// Check if the PlaylistReader has a URL.
        /// </summary>
        /// <returns>True if there are more URLs to read</returns>
        public Boolean hasURL()
        {
            return getURLs().Count > 0;
        }

        /// <summary>
        /// Gets a random URL from the playlist reader.
        /// </summary>
        /// <exception cref="System.InvalidOperationException">Thrown if there are no URLs left to get. Always peek first!</exception>
        /// <returns>A URL String.</returns>
        public String getFirstURL()
        {
            return getURLs().First();
        }

        public String getFirstID()
        {
            const string URL_HEAD = "http://www.youtube.com/watch?v=";
            return getFirstURL().Replace(URL_HEAD, "");
        }

        /// <summary>
        /// Shuffles the playlist of URLs.
        /// </summary>
        public void shuffleURLs()
        {
            // Using an extension found in PlaylistUtils.
            getURLs().Shuffle();
        }
#endregion
    }
}
