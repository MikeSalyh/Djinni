using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace temporal_lobe
{
    class Program
    {
        static void Main(string[] args)
        {
            System.Console.WriteLine("Starting alarm test...");
            new Alarm("00:00:30");
            Console.ReadLine();
        }
    }
}
