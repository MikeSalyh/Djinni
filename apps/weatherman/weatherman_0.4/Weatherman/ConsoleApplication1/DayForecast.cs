using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Djinni
{
    class DayForecast
    {
        #region Consts & Vars
        
        // The name of the day
        private String day_name;

        // The weather conditions of the day
        private String condition;

        //The daily high temp (F)
        private String high;

        //The daily low temp (F)
        private String low;

        //The icon that the UI will display
        private String icon;
        
        #endregion

        #region Getters

        /// <summary>
        /// The name of the day, ie "Mon", "Tues", etc.
        /// </summary>
        public String getDayName()
        {
            return day_name;
        }

        /// <summary>
        /// The weather conditions for the day
        /// </summary>
        public String getCondition()
        {
            return condition;
        }

        /// <summary>
        /// The daily high temp (F)
        /// </summary>
        public String getHigh()
        {
            return high;
        }

        /// <summary>
        /// The daily low temp (F)
        /// </summary>
        public String getLow()
        {
            return low;
        }

        /// <summary>
        /// The icon that the UI will display (F)
        /// </summary>
        public String getIcon()
        {
            return icon;
        }


        #endregion

        #region Methods

        /// <summary>
        /// This object represents a single day's forcast.
        /// </summary>
        /// <param name="day_name">The day name (Ie, "Mon", "Tues", etc.)</param>
        /// <param name="condition">The weather conditions (Ie, "Clear", "Cloudy", etc.)</param>
        /// <param name="high">The daily high temp in Farenheit, as a String. Do not add a degree symbol.</param>
        /// <param name="low">The daily low temp in Farenheit, as a String. Do not add a degree symbol.</param>
        /// <param name="icon">The icon code, to be used by the UI</param>
        public DayForecast( String day_name, String condition, String high, String low, String icon)
        {
            this.day_name = day_name;
            this.condition = condition;
            this.high = high;
            this.low = low;
            this.icon = icon;
        }

        public String toString()
        {
            return getDayName() + ": " + getCondition() + ". High: " + getHigh() + ", Low: " + getLow();
        }

        #endregion
    }
}
