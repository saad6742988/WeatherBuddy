package com.example.weatherbuddy.ui.splashScreen

import androidx.lifecycle.ViewModel
import com.example.weatherbuddy.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) :ViewModel(){
    fun test():String
    {
       return weatherRepository.test()
    }
}