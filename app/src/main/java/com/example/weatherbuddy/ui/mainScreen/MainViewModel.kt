package com.example.weatherbuddy.ui.mainScreen

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherbuddy.openWeatherService.CityData
import com.example.weatherbuddy.openWeatherService.WeatherData
import com.example.weatherbuddy.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
):ViewModel() {
    private val _selectedCity = MutableStateFlow(CityData("DE",52.517,13.388,"Berlin",null))
    var selectedCity = _selectedCity.asStateFlow()
    private val _weatherData = MutableStateFlow(WeatherData())
    val weatherData = _weatherData.asStateFlow()
    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    private val _citiesList = MutableStateFlow(listOf<CityData>())
    var citiesList = _citiesList.asStateFlow()

    private val runnable = Runnable {
        repository.getCitiesList(_searchText.value).enqueue( object: Callback<List<CityData>>{
            override fun onResponse(p0: Call<List<CityData>>, res: Response<List<CityData>>) {
                Log.d("CityDataCall", "onResponse: ")
                if (res.isSuccessful && res.body()!=null)
                {
                    Log.d("CityDataCall", "onResponse: Success ")
                    _citiesList.value = res.body()!!
                }
            }

            override fun onFailure(p0: Call<List<CityData>>, p1: Throwable) {
                Log.d("CityDataCall", "onFailure: ")
            }

        })
    }
    private val delayCitySearchCall = Handler(Looper.getMainLooper())



    fun onSearchTextChange(text: String) {
        _searchText.value = text
        delayCitySearchCall.removeCallbacks(runnable)
        delayCitySearchCall.postDelayed(runnable,1000)
    }

    fun onToggleSearch(active:Boolean) {
        _isSearching.value = active
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun setSelectedCity(city:CityData)
    {
        _selectedCity.value = city
        repository.getWeatherData(city.lat,city.lon).enqueue(object: Callback<WeatherData>{
            override fun onResponse(p0: Call<WeatherData>, res: Response<WeatherData>) {
                Log.d("GetWeatherData", "onResponse: ")
                if (res.isSuccessful)
                {
                    Log.d("GetWeatherData", "onResponseSuccess: ")
                    Log.d("GetWeatherData", "${round(res.body()!!.main.temp)}   ${round(res.body()!!.main.temp_min)}   ${round(res.body()!!.main.temp_max)}")
                    _weatherData.value = res.body()!!
                }
            }

            override fun onFailure(p0: Call<WeatherData>, p1: Throwable) {
                Log.d("GetWeatherData", "onFailure: ")
            }

        })
    }
}

class DummyModel:ViewModel() {
    private val _selectedCity = MutableStateFlow(cities[0])
    var selectedCity = _selectedCity.asStateFlow()
    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    private val _citiesList = MutableStateFlow(cities)
    val citiesList = searchText.combine(_citiesList){
            text,countries ->
        when {
            text.isNotEmpty() -> {
                countries.filter { country ->
                    country.contains(text, ignoreCase = true)
                }
            }

            else -> countries
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _citiesList.value
    )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToggleSearch(active :Boolean) {
        _isSearching.value = active
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }
    fun setSelectedCity(city:CityData)
    {
//        _selectedCity.value = city
    }
}
val cities = arrayOf("Berlin","Cottbus","Bonn","Munich")