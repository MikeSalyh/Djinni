package app_weatherman;

import app_weatherman.yahoo.*;


public class Weatherman {

	public static void main(String[] args){
		YahooWeatherData j = new YahooWeatherData( 2354314);
		System.out.println( j.getLocation().toString());
		System.out.println( j.getWeather().toString());
		System.out.println( j.getAstronomy().toString());
		System.out.println( j.getAtmosphere().toString());
		System.out.println( j.getWind().toString());
		System.out.println("------------------");
		System.out.println( j.getForecast().getForecastOf(0).toString());
		System.out.println( j.getForecast().getForecastOf(1).toString());
		System.out.println( j.getForecast().getForecastOf(2).toString());
		System.out.println( j.getForecast().getForecastOf(3).toString());
		System.out.println( j.getForecast().getForecastOf(4).toString());
	}

}
