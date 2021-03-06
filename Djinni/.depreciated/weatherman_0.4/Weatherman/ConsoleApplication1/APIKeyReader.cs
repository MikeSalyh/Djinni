﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace Djinni
{
    class APIKeyReader
    {

#region Conts & Vars
        // The absolute path of the API key. Includes the root filepath.
        private String path;

        // The API key is held in this string.
        private String key;

#endregion

#region Methods
        /// <summary>
        /// This class reads an unencrypted API key from a text file.
        /// <para/>The text file must contain nothing but the key.
        /// </summary>
        /// <param name="file_path">The full path to the files directory. For example, "C:\Foo\"</param>
        /// <param name="file_name">The name of the file to read, including its extension. For example, "Bar.txt"</param>
        public APIKeyReader( String file_path, String file_name)
        {
           path = file_path + file_name;
        }

        /// <summary>
        /// This class reads an unencrypted API key from a text file.
        /// <para/>The text file should contain nothing but the key.
        /// </summary>
        /// <param name="file_path_and_name">The full path and name of the file to read. For example, "C:\Foo\Bar.txt"</param>
        public APIKeyReader( String file_path_and_name)
        {
            path = file_path_and_name;
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
