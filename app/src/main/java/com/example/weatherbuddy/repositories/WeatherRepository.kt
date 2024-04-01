package com.example.weatherbuddy.repositories

import android.util.Log
import com.example.weatherbuddy.openWeatherService.CityData
import com.example.weatherbuddy.openWeatherService.OpenWeatherDataService
import com.example.weatherbuddy.openWeatherService.OpenWeatherGeoCodingService
import com.example.weatherbuddy.openWeatherService.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.log

class WeatherRepository @Inject constructor(
    private val openWeatherGeoCodingService: OpenWeatherGeoCodingService,
    private val openWeatherDataService: OpenWeatherDataService
){
    fun getCitiesList(searchText:String): Call<List<CityData>> {
        return openWeatherGeoCodingService.getCitiesList(searchText)
    }
    fun getWeatherData(lat:Double,lon:Double): Call<WeatherData> {
        return openWeatherDataService.getWeatherData(lat,lon)
    }
    fun test():String
    {
        return "Test Success"
    }
}