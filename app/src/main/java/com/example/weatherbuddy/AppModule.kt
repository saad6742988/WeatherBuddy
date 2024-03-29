package com.example.weatherbuddy

import com.example.weatherbuddy.openWeatherService.OpenWeatherDataService
import com.example.weatherbuddy.openWeatherService.OpenWeatherGeoCodingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGson():GsonConverterFactory = GsonConverterFactory.create()
    @Provides
    @Singleton
    @Named("GeoCoding")
    fun provideRetrofitGeocoding(gson:GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/geo/1.0/")
            .addConverterFactory(gson)
            .build()
    }
    @Provides
    @Singleton
    @Named("WeatherData")
    fun provideRetrofitWeather(gson: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/geo/1.0/")
            .addConverterFactory(gson)
            .build()
    }


    @Provides
    fun providesOpenWeatherGeocodingService(@Named("GeoCoding") retrofit: Retrofit):OpenWeatherGeoCodingService
    {
        return retrofit.create(OpenWeatherGeoCodingService::class.java)
    }
    @Provides
    fun providesOpenWeatherDataService(@Named("WeatherData") retrofit: Retrofit):OpenWeatherDataService
    {
        return retrofit.create(OpenWeatherDataService::class.java)
    }
}

