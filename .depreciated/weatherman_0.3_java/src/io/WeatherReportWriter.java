package io;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import weatherman.WeatherMan;
import weatherman.WeatherReport;

/**
 * This class writes a string to a text file. The string
 * should represent a JSON serialized weather report object.
 * 
 * @author Mike
 * @see weatherman.WeatherReport
 */
public class WeatherReportWriter {
	
//	******************************************************
//					CONSTS & VARS 
//	******************************************************
	
	/** 
	 * The local folder in which all App data is held 
	 */
	public static final String APP_FILE_FOLDER = "app_data";
	
	/** 
	 * The local folder in which all WeatherMan data is held 
	 */
	public static final String WEATHERMAN_FOLDER = "weatherman";
	
	/** 
	 * The absolute path of the API Key. Includes the root file path 
	 */
	private String path;
	
//	--- GETTERS ------------------------------------------
	/**
	 * @return The absolute file path where the writeToFile() method will write
	 */
	public String getFilePath()
	{
		return path;
	}
	
	
//	******************************************************
//						CONSTRUCTOR
//	******************************************************
	
	/**
	 * This class is used to write weather report data to a text file.
	 * 
	 * @param file_name the name of the text file where the report will be written
	 */
	public WeatherReportWriter(String file_name)
	{
		path = WeatherMan.ROOT_FILE_PATH + WeatherMan.SLASH + APP_FILE_FOLDER + WeatherMan.SLASH + WEATHERMAN_FOLDER + WeatherMan.SLASH + file_name;
	}
	
	
//	******************************************************
//					  WRITE METHOD
//	******************************************************
	
	/**
	 * Writes a WeatherReport object to the set filepath.
	 * <p>
	 * If the WeatherMan app data folder (../djinni/app_data/weatherman) is missing, it will be created during the first execution of this method.
	 * 
	 * @param weather_report The WeatherReport to be written to file.
	 * @throws IOException If the root folder is missing, or there is an issue writing to file.
	 */
	public void writeWeatherReport(WeatherReport weather_report) throws IOException
	{
		// Before writing anything to a file, ensure that the root filepath is valid.
		File f = new File(WeatherMan.ROOT_FILE_PATH);
		if( f.exists() && f.isDirectory())
		{
			// The root file directory is valid. Then, check if a WeatherMan folder exists.
			// If one doesn't, create it.
			File weatherman_folder = new File(WeatherMan.ROOT_FILE_PATH + WeatherMan.SLASH + APP_FILE_FOLDER);
			if( !weatherman_folder.exists())
				weatherman_folder.mkdirs();
			
			// Once the folder structure has been validated, write the text file.
			FileWriter write = new FileWriter(path);
			PrintWriter print_line = new PrintWriter(write);
			
			// The PrintWriter will write the specified string to the 
			// file of our choosing. If the file doesn't exist, the
			// FileWriter will create it.

			// When using the PrintWriter, some parameters must be passed to it:
			// '%s' means a string of any length
			// '%n' means write to a new line
			print_line.printf( "%s" + "%n" , weather_report.serialize());
			print_line.close();
		} else {
			throw new IOException("ERROR: Please ensure that the folder " + WeatherMan.ROOT_FILE_PATH + " exists and can be written to.");
		}
	}

}
