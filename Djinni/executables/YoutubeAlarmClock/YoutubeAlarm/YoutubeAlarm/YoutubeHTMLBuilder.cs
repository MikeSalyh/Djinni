using System;
using System.IO;
namespace YoutubeAlarm
{
    public class YoutubeHTMLBuilder
    {
        private String id;
        private String outputFolder;

        private const String HTML_FIRST = "<!DOCTYPE html> <html> <body> <!-- 1. The <iframe> (and video player) will replace this <div> tag. --> <div id=\"player\"></div> <script> var tag = document.createElement('script'); tag.src = \"https://www.youtube.com/iframe_api\"; var firstScriptTag = document.getElementsByTagName('script')[0]; firstScriptTag.parentNode.insertBefore(tag, firstScriptTag); var player; function onYouTubeIframeAPIReady() { player = new YT.Player('player', { height: '390', width: '640', videoId: '";
        private const string HTML_LAST = "', events: { 'onReady': onPlayerReady, 'onStateChange': onPlayerStateChange } }); } function onPlayerReady(event) { event.target.playVideo(); event.target.setVolume(100); } var done = false; function onPlayerStateChange(event) { if (event.data == YT.PlayerState.PLAYING && !done) { setTimeout(stopVideo, 6000); done = true; } } function stopVideo() { player.stopVideo(); } </script> </body> </html>";
       
        private const String HTML_FILE = "YoutubeVideo.html";

        public YoutubeHTMLBuilder(String outputFolder, String YoutubeVideoID)
        {
            // Takes in a YouTube ID, and builds an HTML page around that video.
            this.id = YoutubeVideoID;
            this.outputFolder = outputFolder;
        }



        /// <summary>
        /// Returns the file path and name of the current HTML page.
        /// </summary>
        /// <returns>The log file's name and path.</returns>
        public String GetFilepath()
        {
            return Path.Combine(outputFolder, HTML_FILE);
        }

        /// <summary>
        /// Given a caller and message, writes them to the log file.
        /// </summary>
        /// <param name="caller">The name of the object that's writing the log.</param>
        /// <param name="caller_version">The version of the caller object.</param>
        /// <param name="message">The message to be logged.</param>
        /// <returns>True if write was successful. False otherwise.</returns>
        public bool writeHTML()
        {
            //First, check if the log directory exists:
            if (Directory.Exists(outputFolder))
            {
                // If the directory exists, attempt to write the log.
                try
                {
                    CreateHTML();
                    return true;
                }
                catch (Exception e)
                {
                    //There's been an exception. The log could not be written.
                    Console.WriteLine("YoutubeHTMLBuilder: Error Writing HTML.");
                    Console.WriteLine(e.Message);
                    Console.WriteLine();
                    return false;
                }
            }
            else
            {
                // If the directory does not exist, this is an error. Do not write anything to disk.
                Console.WriteLine("YoutubeHTMLBuilder: Error Writing Log.");
                Console.WriteLine("Could not find dir {0}.", outputFolder);
                Console.WriteLine();
                return false;
            }
        }

        /// <summary>
        /// Check whether or not a HTML file exists.
        /// </summary>
        /// <returns>True if the file exists.</returns>
        private bool HTMLExists()
        {
            return File.Exists(Path.Combine(outputFolder, HTML_FILE));
        }

        public string getHTML()
        {
            return Path.Combine(outputFolder, HTML_FILE);
        }


        /// <summary>
        /// Create a new HTML file.
        /// </summary>
        private void CreateHTML()
        {
            File.Create(Path.Combine(outputFolder, HTML_FILE)).Close();
            Console.WriteLine("YoutubeHTMLBuilder: Created a new HTML file in {0}", outputFolder);

            //WRITE THE MESSAGE
            String output = HTML_FIRST + id + HTML_LAST;

            // Write the line into the log file.
            using (TextWriter text_writer = new StreamWriter(Path.Combine(outputFolder, HTML_FILE), append: false))
            {
                text_writer.WriteLine(output);
                text_writer.Close();
            }

            // Then, write the line into the console.
            Console.WriteLine(output);
        }


    }
}
