package com.example.weatherbuddy.openWeatherService

import com.google.gson.annotations.SerializedName

data class CityData(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("state")
    val state: String?
)