using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Weatherman.Yahoo;
using Newtonsoft.Json;

namespace Weatherman
{
    class Program
    {
        /* SALYH 9/21/14
         * WEATHERMAN: When run, this app will give the user weather information about the city of LOS ANGELES.
         * Then, it will terminate after 5 seconds. For more information, see https://docs.google.com/document/d/149nV0z7M7u6Qo0lKRcpDVjJnb7DPAPTSILJUXb028CI/edit?usp=drive_web
         * This current implementation is based off of a Java module I wrote in June. 
         */

        // This variable represents our location. Based on Yahoo's WOEID (Gathered from http://woeid.rosselliot.co.nz/)
        private static readonly int MY_LOCATION = 2518724; // Westwood, Los Angeles, CA

        static void Main(string[] args)
        {
            System.Threading.Thread.Sleep(1000);
        }
    }
}
