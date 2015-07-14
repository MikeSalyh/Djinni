using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace Djinni
{
    class APIKeyReader
    {

#region Conts & Vars

        // The folder in which all API keys are held.
        public const String KEY_FOLDER = "keys\\";

        // The absolute path of the API key. Includes the root filepath.
        private String path;

        // The API key is held in this string.
        private String key;

#endregion

#region Methods
        /// <summary>
        /// This class reads an unencrypted API key from a text file.
        /// The text file must contain nothing but the key.
        /// </summary>
        /// <param name="file_name">The name of the .txt file to read.</param>
        public APIKeyReader( String file_name)
        {
           path = WeatherMan.ROOT_FILE_PATH + APIKeyReader.KEY_FOLDER + file_name;
        }

        /// <summary>
        /// Returns the API key. The first time this method is called, the key is read from the text file.
        /// In subsequent calls, the key is retrieved from memory.
        /// </summary>
        /// <returns>An API key as a String</returns>
        /// <exception cref="System.IO.IOException">Thrown when the file cannot be read.</exception>
        /// <exception cref="System.OutOfMemoryException">Thrown when there is insufficient memory to allocate a buffer for the returned string.</exception>
        public String getKey()
        {
            return key == null ? key = readKeyFromFile(path) : key; 
        }

        /// <summary>
        /// Reads the first line of the text file, and returns it as a String.
        /// </summary>
        /// <param name="filepath">The absolute filepath to the text file containing the key</param>
        /// <returns>Returns the API key as a String</returns>
        /// <exception cref="System.IO.IOException">Thrown when the file cannot be read.</exception>
        /// <exception cref="System.OutOfMemoryException">Thrown when there is insufficient memory to allocate a buffer for the returned string.</exception>
        private String readKeyFromFile( String filepath)
        {
            using ( StreamReader stream_reader = new StreamReader(filepath))
            {
                return stream_reader.ReadToEnd();
            }
        }

#endregion
    }
}
