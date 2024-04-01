package com.example.weatherbuddy.openWeatherService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherGeoCodingService {
    @GET("direct")
    fun getCitiesList(
        @Query("q")city:String,
        @Query("limit")limit:String="10",
        @Query("appid")appid:String = "1686fb0c6fca26dff30036c0f3609de7"

    ): Call<List<CityData>>
}
interface OpenWeatherDataService {
    @GET("weather")
    fun getWeatherData(
        @Query("lat")lat:Double,
        @Query("lon")lon:Double,
        @Query("units")units:String = "metric",
        @Query("appid")appid:String = "1686fb0c6fca26dff30036c0f3609de7"
    ):Call<WeatherData>
}