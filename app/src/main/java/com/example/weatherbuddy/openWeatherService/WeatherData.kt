package com.example.weatherbuddy.openWeatherService

data class WeatherData(
    val base: String = "", // stations
    val clouds: Clouds = Clouds(),
    val cod: Int = 0, // 200
    val coord: Coord = Coord(),
    val dt: Int = 0, // 1661870592
    val id: Int = 0, // 3163858
    val main: Main = Main(),
    val name: String = "", // Zocca
    val rain: Rain = Rain(),
    val sys: Sys = Sys(),
    val timezone: Int = 0, // 7200
    val visibility: Int = 0, // 10000
    val weather: List<Weather> = listOf(),
    val wind: Wind = Wind()
)
data class Clouds(
    val all: Int = 0 // 100
)

data class Coord(
    val lat: Double = 0.0, // 44.34
    val lon: Double = 0.0 // 10.99
)

data class Main(
    val feels_like: Double = 0.0, // 298.74
    val grnd_level: Int = 0, // 933
    val humidity: Int = 0, // 64
    val pressure: Int = 0, // 1015
    val sea_level: Int = 0, // 1015
    val temp: Double = 0.0, // 298.48
    val temp_max: Double = 0.0, // 300.05
    val temp_min: Double = 0.0 // 297.56
)

data class Rain(
    val `1h`: Double = 0.0 // 3.16
)

data class Sys(
    val country: String = "", // IT
    val id: Int = 0, // 2075663
    val sunrise: Int = 0, // 1661834187
    val sunset: Int = 0, // 1661882248
    val type: Int = 0 // 2
)

data class Weather(
    val description: String = "", // moderate rain
    val icon: String = "", // 10d
    val id: Int = 0, // 501
    val main: String = "" // Rain
)

data class Wind(
    val deg: Int = 0, // 349
    val gust: Double = 0.0, // 1.18
    val speed: Double = 0.0 // 0.62
)