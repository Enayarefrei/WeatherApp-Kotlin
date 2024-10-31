package com.example.weatherapp

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
    val sunset: Int,
)

data class Weather (
    val description: String,
    val icon: String,
)
