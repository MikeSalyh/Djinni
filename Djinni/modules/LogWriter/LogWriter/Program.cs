using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Module.LogWriter
{
    class Program
    {
        static void Main(string[] args)
        {
            LogWriter lw = new LogWriter("C:\\Users\\Mike\\Documents\\Home Automation\\Tests");
            lw.Log("Djinni Home", "v0.0.2", "Test?");
            Console.ReadLine();
        }
    }
}
