package app_weatherman;

import app_weatherman.yahoo.*;


public class Weatherman {

	public static void main(String[] args){
		YahooWeatherData j = new YahooWeatherData( 2354314);
		System.out.println( j.getLocation().getCity());
	}

}
