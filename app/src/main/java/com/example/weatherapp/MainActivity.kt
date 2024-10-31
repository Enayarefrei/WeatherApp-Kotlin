package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var etCity: EditText
    private lateinit var btnGetWeather: Button
    private lateinit var tvWeatherResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCity = findViewById(R.id.etCity)
        btnGetWeather = findViewById(R.id.btnGetWeather)
        tvWeatherResult = findViewById(R.id.tvWeatherResult)

        btnGetWeather.setOnClickListener {
            val city = etCity.text.toString()
            if (city.isEmpty()) {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            } else {
                getWeatherData(city)
            }
        }
    }

    private fun getWeatherData(city: String) {
        val apiKey = "fc3a3d777494d7ede3c48d4e31a33104" // Replace with your actual API key
        RetrofitClient.instance.getCurrentWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    val temperature = weatherResponse?.main?.temp
                    val description = weatherResponse?.weather?.get(0)?.description
                    tvWeatherResult.text = "Temperature: $temperature°C\nDescription: $description"
                } else {
                    Toast.makeText(this@MainActivity, "City not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
