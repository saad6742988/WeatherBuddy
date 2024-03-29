package com.example.weatherbuddy.repositories

import android.util.Log
import com.example.weatherbuddy.openWeatherService.CityData
import com.example.weatherbuddy.openWeatherService.OpenWeatherGeoCodingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
class WeatherRepository @Inject constructor(
    private val openWeatherGeoCodingService: OpenWeatherGeoCodingService
){
    fun test():String
    {
        return "Test Success"
    }
}