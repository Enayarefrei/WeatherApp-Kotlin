package com.example.weatherapp

import android.os.Bundle
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCity = findViewById(R.id.etCity)
        btnGetWeather = findViewById(R.id.btnGetWeather)

        // Initialize TextViews for displaying weather details
        val tvCityName = findViewById<TextView>(R.id.tvCityName)
        val tvTemperature = findViewById<TextView>(R.id.tvTemperature)
        val tvWeatherDescription = findViewById<TextView>(R.id.tvWeatherDescription)
        val tvHumidity = findViewById<TextView>(R.id.tvHumidity)
        val tvWindSpeed = findViewById<TextView>(R.id.tvWindSpeed)
        val weatherDetailsContainer = findViewById<View>(R.id.weatherDetailsContainer)

        btnGetWeather.setOnClickListener {
            val city = etCity.text.toString()
            if (city.isEmpty()) {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            } else {
                getWeatherData(city) { weather ->
                    // Show the weather details container
                    weatherDetailsContainer.visibility = View.VISIBLE

                    // Set the text for each field
                    tvCityName.text = weather.cityName
                    tvTemperature.text = "${weather.temperature}°C"
                    tvWeatherDescription.text = weather.description
                    tvHumidity.text = "Humidity: ${weather.humidity}%"
                    tvWindSpeed.text = "Wind Speed: ${weather.windSpeed} m/s"
                }
            }
        }
    }


    private fun getWeatherData(city: String, onWeatherReceived: (WeatherData) -> Unit) {
        val apiKey = "fc3a3d777494d7ede3c48d4e31a33104"
        RetrofitClient.instance.getCurrentWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()


                    if (weatherResponse != null) {
                        val weatherData = WeatherData(
                            cityName = weatherResponse.name,
                            temperature = weatherResponse.main.temp,
                            description = weatherResponse.weather[0].description,
                            humidity = weatherResponse.main.humidity,
                            windSpeed = weatherResponse.wind.speed
                        )
                        onWeatherReceived(weatherData)
                    }
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
data class WeatherData(
    val cityName: String,
    val temperature: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double
)


