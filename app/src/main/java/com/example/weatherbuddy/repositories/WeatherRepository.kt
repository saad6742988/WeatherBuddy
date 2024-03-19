package com.example.weatherbuddy.repositories

import javax.inject.Inject

class WeatherRepository @Inject constructor(){
    fun test():String
    {
        return "Test Success"
    }
}