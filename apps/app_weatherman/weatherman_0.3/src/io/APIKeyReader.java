package io;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * This class reads an API key from a .txt file
 * 
 * @author Mike
 *
 */
public class APIKeyReader {

//	******************************************************
//    					CONSTS & VARS 
//	******************************************************
	
	/** 
	 * The folder in which all API keys are held 
	 */
	public static final String ROOT_FILE_PATH = "C:\\ProgramData\\Djinni\\keys\\";
	
	/** 
	 * The absolute path of the API Key. Includes the root file path 
	 */
	private String path;
	
	/**
	 * The API key is held in this String. 
	 */
	private String key;
	
	
//	******************************************************
//						METHODS
//	******************************************************
	
	/**
	 * This class reads an API key from a text file.
	 * The file should contain nothing but the key. 
	 * 
	 * @param file_name The name of the .txt file to read.
	 */
	public APIKeyReader( String file_name)
	{
		path = ROOT_FILE_PATH + file_name;
	}
	
	/**
	 * Returns the API key. The first time this method is called, 
	 * the key is read from the text file. In subsequent calls, the key
	 * is retrieved from memory.
	 * 
	 * @return the API key as a String
	 * @throws IOException
	 */
	public String getKey() throws IOException
	{
		
		if( key != null)
			return key;
		else
			return key = readKeyFromFile(path);
	}

	/**
	 * Reads the first line of the text file and returns it as a String.
	 * 
	 * @param filepath The absolute filepath to the text file containing the key
	 * @return Returns the API key as a string
	 * @throws IOException if there is an issue with the text file.
	 */
	private String readKeyFromFile(String filepath) throws IOException
	{
		BufferedReader textReader = new BufferedReader(new FileReader(filepath));
		String textData = textReader.readLine();
		textReader.close();
		return textData;
	}
}
