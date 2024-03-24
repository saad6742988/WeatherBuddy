package com.example.weatherbuddy.ui.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherbuddy.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
):ViewModel() {
    private val _selectedCity = MutableStateFlow(cities[0])
    var selectedCity = _selectedCity.asStateFlow()
    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()
    var abc = MutableStateFlow("")
        private set

    private val _citiesList = MutableStateFlow(cities)
    val citiesList = searchText.combine(_citiesList){
        text,cities ->
        when {
            text.isNotEmpty() -> {
                cities.filter { country ->
                    country.contains(text, ignoreCase = true)
                }
            }

            else -> {
                Log.d("citiesList", "return complete")
                cities.toList()
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _citiesList.value
    )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToggleSearch(active:Boolean) {
        Log.d("SearchBar", "onToggleSearch: $abc")
        _isSearching.value = active
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun setSelectedCity(city:String)
    {
        _selectedCity.value = city
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
    fun setSelectedCity(city:String)
    {
        _selectedCity.value = city
    }
}
val cities = arrayOf("Berlin","Cottbus","Bonn","Munich")