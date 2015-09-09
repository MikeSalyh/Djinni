using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Module.WeatherMan
{
    class Program
    {
        static void Main(string[] args)
        {
            WeatherMan wm = new WeatherMan("b3b04a47e7db69a0");
            WeatherReport w = wm.GetWeatherReport();
            Console.ReadLine();
        }
    }
}
