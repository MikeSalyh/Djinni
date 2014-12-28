using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace Weatherman.Yahoo
{
    // ASTRONOMY
    // Today's astronomy data. Gathered from Yahoo Weather data
    public class Astronomy
    {

        #region Variables
        private static readonly string YWeatherTag = "yweather:astronomy";
        private string _sunrise, _sunset;
        #endregion

        #region Constructor

        /* An object like this has two possible constructors.
         * The first is the 'XML' approach. Pass it an XMLNode and
         * a namespace manager, and it will attempt to extract the
         * valid data from the node. This is the safest method.
         * The second is the 'brute force' approach. Pass it the
         * exact parameters.
         */

        public Astronomy(XmlNode channel, XmlNamespaceManager manager)
        {
            string sunrise = channel.SelectSingleNode(YWeatherTag, manager).Attributes["sunrise"].Value;
            string sunset = channel.SelectSingleNode(YWeatherTag, manager).Attributes["sunset"].Value;
            Initialize(sunrise, sunset);
        }

        public Astronomy( String sunrise, String sunset)
	    {
            Initialize(sunrise, sunset);
	    }

        private void Initialize( String sunrise, String sunset)
        {
            this._sunrise = sunrise;
            this._sunset = sunset;
        }
        #endregion

        #region Getters
        /* Note there are NO SETTERS. The values are locked into place
         * when the object is initialized.
         */
        public string sunrise{
            get{    return _sunrise;    }
        }

        public string sunset
        {
            get {   return sunset;      }
        }

        #endregion

        #region To-String
        public string sunriseToString(){	return "Sunrise: " + sunrise;	}
	    public string sunsetToString(){		return "Sunset: " + sunset;		}
	
	    public string toString()
	    {
            return sunriseToString() + ". " + sunsetToString() + ".";
        }
        #endregion
    }
}
