using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Speech.Synthesis;
using System.Collections;

namespace Module.Vox
{
    /// <summary>
    /// Vox is the module responsible for speaking (audio) to the user.
    /// </summary>
    public class Vox : IDisposable
    {
        /// <summary> The speech synethesizer for Vox </summary>
        SpeechSynthesizer synth;

        /// <summary> The voice with which Vox speaks. This is a SAPI voice. </summary>
        private const String VOX_VOICE = "Microsoft Zira Desktop";

        // The array lists that holds all of the items Vox is to speak. There are three lists, differentiating the differant priorities of messages.
        private Queue<String> speechQueue_High = new Queue<string>();
        private Queue<String> speechQueue_Med = new Queue<string>();
        private Queue<String> speechQueue_Low = new Queue<string>();

        /// <summary>Whether or not Vox is speaking.</summary>
        private bool speaking = false;

        /// <summary>
        /// The priority of the spoken string. When Vox speaks, if multiple audio files are queued up, they will play back in priority order, based on the order they were recieved.
        /// </summary>
        public enum Priority {  low, medium, high}

        /// <summary>
        /// True if this Vox has been disposed.
        /// </summary>
        private bool disposed = false;

        /// <summary>
        /// Create an instance of Vox Speech module.
        /// </summary>
        public Vox()
        {
            // Create the speech synthesizer object.
            synth = new SpeechSynthesizer();
           
            // Configure the audio output. 
            synth.SetOutputToDefaultAudioDevice();
            synth.SelectVoice(VOX_VOICE);

            // Add Event Listener for Completed Speaking
            synth.SpeakCompleted += new EventHandler<SpeakCompletedEventArgs>(synth_SpeakCompleted);

        }


        #region Public Methods

        /// <summary>
        /// Dispose of the SpeechSynthesizer associated with this class. If this method is called, the Vox should be discarded afterwards.
        /// </summary>
        public void Dispose()
        {
            if (!disposed)
            {
                Clear();
                synth.Dispose();
                disposed = true;
            }
        }

        /// <summary>
        /// Queues Vox to speak a given line.  When Vox speaks, if multiple audio files are queued up, they will play back in priority order, based on the order they were recieved.
        /// </summary>
        /// <param name="phrase">The phrase to be spoken</param>
        public void Queue( String phrase)
        {
            //If this method is invoked without priority, by default assign priority medium.
            Queue(phrase, Priority.medium);
        }

        /// <summary>
        /// Queues Vox to speak a given line. When Vox speaks, if multiple audio files are queued up, they will play back in priority order, based on the order they were recieved.
        /// <para/>Note, high priority phrases will not interupt low-priority phrases, they will simply stack below them.
        /// </summary>
        /// <param name="phrase">The phrase to be spoken</param>
        /// <param name="priority">The priority of the spoken line. See Vox.Priority</param>
        public void Queue(String phrase, Priority priority)
        {
            // Ensure that a valid priority was provided.
            switch( priority)
            {
                case Priority.high:
                    // queue high-priority message
                    speechQueue_High.Enqueue(phrase);
                    break;
                case Priority.medium:
                    // queue medium-priority message
                    speechQueue_Med.Enqueue(phrase);
                    break;
                case Priority.low:
                    // queue low-priority message
                    speechQueue_Low.Enqueue(phrase);
                    break;
                default:
                    throw new ArgumentException("Invalid priority. Use Vox.Priority.");
            }

            //Once the phrase has been queued, if Vox isn't already speaking, speak.
            if( !speaking)
            {
                DequeueAndSpeak();
            }
        }

        /// <summary>
        /// Empties out Vox's speech queue, and stops it from speaking.
        /// </summary>
        public void Clear()
        {
            // Stop the synethesizer & cancel all calls it has queued
            synth.SpeakAsyncCancelAll();

            // Then clear the queues
            speechQueue_High.Clear();
            speechQueue_Med.Clear();
            speechQueue_Low.Clear();
        }

        #endregion

        #region Private Methods
        
        /// <summary>
        /// Check if there is any speech enqueued.
        /// </summary>
        /// <returns>False if every speech queue is empty. True otherwise.</returns>
        private bool queueIsFull()
        {
            return (speechQueue_High.Count > 0 || speechQueue_Med.Count > 0 || speechQueue_Low.Count > 0);
        }


        /// <summary>
        /// Checks the queues for the highest priority line to speak, and speaks it.
        /// </summary>
        private void DequeueAndSpeak()
        {
            // First, start on the high-priority queue. If it has any elements in it, speak these first
            String myPhrase;
            if( speechQueue_High.Count > 0)
            {
                myPhrase = speechQueue_High.Dequeue();
            } else if( speechQueue_Med.Count > 0)
            {
                myPhrase = speechQueue_Med.Dequeue();
            } else if( speechQueue_Low.Count > 0)
            {
                myPhrase = speechQueue_Low.Dequeue();
            }
            else
            {
                throw new InvalidOperationException("Cannot dequeue when all queues are empty. Be sure to call getQueueIsEmpty() before calling this method.");
            }
            Speak(myPhrase);
        }

        /// <summary>
        /// Async speaks a given phrase, from the synethesizer.
        /// </summary>
        /// <param name="phrase">The phrase to speak</param>
        private void Speak(String phrase)
        {
            // set speaking to true, because Vox is speaking.
            speaking = true;
            synth.SpeakAsync(phrase);
        }


        /// <summary>
        /// When a line is finished speaking, this method is invoked. It checks if there is any more speech in the queue, and if so fires it off.
        /// </summary>
        private void synth_SpeakCompleted(object sender, SpeakCompletedEventArgs e)
        {
            speaking = false;
            if (queueIsFull())
            {
                DequeueAndSpeak();
            }
        }

        #endregion
    }
}
