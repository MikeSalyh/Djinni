using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Module.LogWriter
{
    /// <summary>
    /// Write a message into the log.
    /// </summary>
    class StandaloneLogWriter
    {
        /// <summary>
        /// The main method. There are three valid call signatures:
        /// <para/>folder, message
        /// <para/>folder, caller, message
        /// <para/>folder, caller, caller version #, message
        /// </summary>
        /// <param name="args">The caller, version number, and/or message to be written to the log.</param>
        /// <returns>0 if successful. 1 if not.</returns>
        static int Main(string[] args)
        {
            // Check if the call signature is valid.
            if( args.Length < 2 || args.Length > 4)
            {
                // Less than 2 params, or more than 4 is not valid.
                Console.WriteLine("Invalid number of args. Valid call signatures are: \n[folder, message] \n[folder, caller, message] \n[folder, caller, version, message]");

                //Return 1 for unsuccessful.
                return 1;
            }
            else
            {
                // If the args are valid, write them into the log file.
                LogWriter log_writer = new LogWriter(args[0]);
                
                // create a bool to hold the outcome of the log writing.
                bool wasSuccessful = false;
                // There are three valid signatures:
                if( args.Length == 2)
                {
                   wasSuccessful = log_writer.Log(args[1]);
                }
                else if( args.Length == 3)
                {
                    wasSuccessful = log_writer.Log(args[1], args[2]);
                }
                else if(args.Length == 4)
                {
                    wasSuccessful = log_writer.Log(args[1], args[2], args[3]);
                }

                //Return 0 for successful. Return 1 for unsuccessful
                return (wasSuccessful ? 0 : 1);
            }
        }
    }
}
