﻿using System;
using System.Linq;
using System.Text;
using System.IO;
using System.Collections;

namespace Module.LogWriter
{
    /// <summary>
    /// The log writer module writes event logs to memory.
    /// </summary>
    public class LogWriter
    {
        #region Variables & Consts
        /// <summary>
        /// The stable version of the module.
        /// </summary>
        public const String VERSION = "0.1";

        /// <summary>
        /// The location of the folder where Djinni's logs will be written.
        /// </summary>
        private String log_folder_location;

        /// <summary>
        /// The name of the log file.
        /// </summary>
        private const String LOG_FILE_NAME = "log.txt";

        #endregion

        #region Constructor
        /// <summary>
        /// Create a log writer. The log writer takes in a filepath as a parameter.
        /// </summary>
        /// <param name="log_folder_location"></param>
        public LogWriter( String log_folder_location)
        {
            //Set the log folder location
            this.log_folder_location = log_folder_location;
        }

        #endregion

        #region Public Methods

        /// <summary>
        /// Given a message, writes that message to the log file.
        /// </summary>
        /// <param name="message">The message to be logged.</param>
        /// <returns>True if write was successful. False otherwise.</returns>
        public bool Log(String message)
        {
            return Log("", message);
        }

        /// <summary>
        /// Given a caller and message, writes them to the log file.
        /// </summary>
        /// <param name="caller">The name of the object that's writing the log.</param>
        /// <param name="message">The message to be logged.</param>
        /// <returns>True if write was successful. False otherwise.</returns>
        public bool Log(String caller, String message)
        {
            return Log(caller, "", message);
        }

        /// <summary>
        /// Given a caller and message, writes them to the log file.
        /// </summary>
        /// <param name="caller">The name of the object that's writing the log.</param>
        /// <param name="caller_version">The version of the caller object.</param>
        /// <param name="message">The message to be logged.</param>
        /// <returns>True if write was successful. False otherwise.</returns>
        public bool Log(String caller, String caller_version, String message)
        {
            //First, check if the log directory exists:
            if (Directory.Exists(log_folder_location))
            {
                // If the directory exists, attempt to write the log.
                try
                {
                    //First, check if a log file already exists.
                    if(!LogExists())
                    {
                        CreateLog();
                    }

                    // Then, log the message.
                    LogMessage(caller, caller_version, message);
                    return true;
                }
                catch (Exception e)
                {
                    //There's been an exception. The log could not be written.
                    Console.WriteLine("LogWriter: Error Writing Log.");
                    Console.WriteLine(e.Message);
                    Console.WriteLine();
                    return false;
                }
            }
            else
            {
                // If the directory does not exist, this is an error. Do not write anything to disk.
                Console.WriteLine("LogWriter: Error Writing Log.");
                Console.WriteLine("Could not find dir {0}.", log_folder_location);
                Console.WriteLine();
                return false;
            }
        }

        /// <summary>
        /// Returns the folder in which this LogWriter's logs are stored.
        /// </summary>
        /// <returns>The folder where this LogWriter's logs are stored.</returns>
        public String GetLogDirectory()
        {
            return log_folder_location;
        }

        /// <summary>
        /// Returns the file path and name of the currently active log file.
        /// </summary>
        /// <returns>The log file's name and path.</returns>
        public String GetLogFilepath()
        {
            return Path.Combine(log_folder_location, LogWriter.LOG_FILE_NAME);
        }

        #endregion

        #region Private Methods

        /// <summary>
        /// Check whether or not a log file exists.
        /// <para/>This method would be more complex if multiple log files are needed.
        /// </summary>
        /// <returns>True if the log file exists.</returns>
        private bool LogExists()
        {
            return File.Exists( Path.Combine(log_folder_location, LogWriter.LOG_FILE_NAME));
        }

        /// <summary>
        /// Create a new log.
        /// <para/>This method would become more complex if multiple log files are needed.
        /// </summary>
        /// <returns>The filestream that was created.</returns>
        private void CreateLog()
        {
           File.Create( Path.Combine(log_folder_location, LogWriter.LOG_FILE_NAME)).Close();
           Console.WriteLine("LogWriter: Created a new log file in {0}", log_folder_location);

           const String CREATION_MESSAGE = "New log file created.";
           LogMessage("LogWriter", VERSION, CREATION_MESSAGE);

        }

        /// <summary>
        /// Log a message into the log file, and into the console.
        /// </summary>
        /// <param name="caller">The object that called this log action.</param>
        /// <param name="caller_version">The version number of the caller.</param>
        /// <param name="message">The message to be logged.</param>
        private void LogMessage(String caller, String caller_version, String message)
        {
            String output = GenerateLogString(caller, caller_version, message);

            // Write the line into the log file.
            using( TextWriter text_writer = new StreamWriter( Path.Combine(log_folder_location, LogWriter.LOG_FILE_NAME), append:true))
            {
                text_writer.WriteLine(output);
                text_writer.Close();
            }

            // Then, write the line into the console.
            Console.WriteLine(output); 

        }

        /// <summary>
        /// Generates a single string, human readable version of the log. 
        /// <para/>This method handles optional params in an elegant way.
        /// </summary>
        /// <param name="caller">The object that called this log().</param>
        /// <param name="caller_version">The version of the caller.</param>
        /// <param name="message">The message to be written.</param>
        /// <returns>The log message to be written, including timestamp.</returns>
        private String GenerateLogString(String caller, String caller_version, String message)
        {
            // White space padding.
            const String WHITE_SPACE = "  ";

            // This is the string that will eventually be returned.
            String outString = "";

            // Every log starts with the current datetime.
            outString += DateTime.Now + WHITE_SPACE;

            // If the caller is defined, add it to the log.
            if (caller != String.Empty)
            {
                outString += "{" + caller;
                if( caller_version != String.Empty)
                {
                    // If the caller version is defined, add that to the log too.
                    outString += ":" + caller_version; 
                }
                outString += "}" + WHITE_SPACE;
            }

            // Add the message to the log.
            outString += message;

            return outString;
        }

        #endregion
    }
}
