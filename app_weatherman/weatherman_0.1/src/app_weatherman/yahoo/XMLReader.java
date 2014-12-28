package app_weatherman.yahoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/** The XML READER extends BufferedReader, and has the ability to convert lines of
 * 	XML code into strings. It has two public methods, extract() and readFirstInstanceOf()
 * @author mikesalyh
 */
public class XMLReader extends BufferedReader {

	public XMLReader(Reader in, int sz) {
		super(in, sz);
	}

	public XMLReader(Reader in) {
		super(in);
	}
	
	/** EXTRACT FROM XML
	 * This method gathers a value out of a line of XML code.
	 * Source is the line of XML. Target is the value we wish to extract.
	 * This method returns the result as a string. If the source does not contain
	 * the target, it throws an IO Exception
	 */
	public String extract( String source, String target) throws IOException
	{
		int indexOfTarget = source.indexOf( target);
		
		// If the chosen line does not contain the target variable, throw an IO Exception.
		if( indexOfTarget == -1)
			throw new IOException("Source string does not contain target string.");
		
		// Remove all other parts of the string but the desired value
		source = source.substring( indexOfTarget);
		int indexOfFirstQuotes = source.indexOf("\"") + 1;
		source = source.substring( indexOfFirstQuotes);
		int indexOfLastQuotes = source.indexOf("\"");
		source = source.substring(0, indexOfLastQuotes);		
		return source;
	}
	
	/** READ FIRST INSTANCE OF [STRING]
	 * this method digs through the buffered reader, until it encounters a line with the
	 * target string. It returns that line. The buffered reader is only reset if the
	 * resetReader parameter is set to true. If the target string isn't found, an exception is thrown.
	 */
	public String readFirstInstanceOf( String target, boolean resetReader) throws IOException
	{
		String currentLine;		
		while (( currentLine = super.readLine() ) != null) {
			if( currentLine.contains( target)) {
				break; // once we find this line, end the loop.
			}
		}
		
		// If resetReader == true, reset the buffered reader.
		if( resetReader)
			super.reset();
		
		// If the target line is not found, through an IO Exception
		if( currentLine == null)
			throw new IOException( "This reader does not contain target string. Was the reader properly reset?");
		
		// Lastly, return the line that contains the target.
		return currentLine;
	}
	
	// Overloaded method. Default is not to reset the reader.
	public String readFirstInstanceOf( String target) throws IOException
	{
		return readFirstInstanceOf( target, false);
	}
}
