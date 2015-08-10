using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Timers;


namespace temporal_lobe
{
    /// <summary>
    /// An Alarm.
    /// </summary>
    class Alarm : IDisposable
    {
        /// <summary>
        /// The timer that handles this Alarm.
        /// </summary>
        private Timer timerClock;

        private int clockTime = 0;
        private int alarmTime = 0;

        /// <summary>
        /// Create an Alarmist Module.
        /// </summary>
        public Alarm( String time)
        {
            timerClock = new System.Timers.Timer();
            timerClock.Elapsed += new ElapsedEventHandler(OnTimer);
            timerClock.Interval = 1000;
            timerClock.Enabled = true;
            StartTimer(time);
        }

        private void StartTimer(String input)
        {
            clockTime = 0;
            inputToSeconds(input);
        }

        private void ResetTimer()
        {
            clockTime = 0;
            alarmTime = 0;
        }
        


        /// <summary>
        /// This method is called when the timer ticks.
        /// </summary>
        private void OnTimer( Object source, ElapsedEventArgs e)
        {
            this.clockTime++;
            int countdown = this.alarmTime - this.clockTime;
            if (this.alarmTime != 0)
            {
                Console.WriteLine(secondsToTime(countdown));
            }

            //Sound Alarm
            if (this.clockTime == this.alarmTime)
            {
                Console.WriteLine("Time's Up!");
                //TODO: Write the method for time-up.

                //Then, dispose of this Alarm object.
                Dispose();
            }
        }


        private void inputToSeconds(string timerInput)
        {
            try
            {
                string[] timeArray = new string[3];
                int minutes = 0;
                int hours = 0;
                int seconds = 0;
                int occurence = 0;
                int length = 0;

                occurence = timerInput.LastIndexOf(":");
                length = timerInput.Length;

                //Check for invalid input
                if (occurence == -1 || length != 8)
                {
                }
                else
                {
                    timeArray = timerInput.Split(':');

                    seconds = Convert.ToInt32(timeArray[2]);
                    minutes = Convert.ToInt32(timeArray[1]);
                    hours = Convert.ToInt32(timeArray[0]);

                    this.alarmTime += seconds;
                    this.alarmTime += minutes * 60;
                    this.alarmTime += (hours * 60) * 60;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("inputToSeconds(): " + e.Message);
            }
        }


        /// <summary>
        /// Converts an int of seconds into a string time format.
        /// </summary>
        /// <param name="seconds"></param>
        /// <returns>A string of time time, in HH:MM:SS format.</returns>
        public string secondsToTime(int seconds)
        {
            int minutes = 0;
            int hours = 0;

            while (seconds >= 60)
            {
                minutes += 1;
                seconds -= 60;
            }
            while (minutes >= 60)
            {
                hours += 1;
                minutes -= 60;
            }

            string strHours = hours.ToString();
            string strMinutes = minutes.ToString();
            string strSeconds = seconds.ToString();

            if (strHours.Length < 2)
                strHours = "0" + strHours;
            if (strMinutes.Length < 2)
                strMinutes = "0" + strMinutes;
            if (strSeconds.Length < 2)
                strSeconds = "0" + strSeconds;

            return strHours + ":" + strMinutes + ":" + strSeconds;
        }

        public void Dispose()
        {
            timerClock.Stop();
            timerClock.Dispose();
        }
    }
}
