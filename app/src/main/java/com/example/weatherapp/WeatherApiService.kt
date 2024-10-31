package com.example.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call


interface WeatherApiService {
    @GET("weather")
    fun  getCurrentWeather(
       @Query("q") city: String,
       @Query("appid") apiKey: String,
       @Query("units") units: String = "metric" // We are using Celsius here
    ): Call<WeatherResponse>
}