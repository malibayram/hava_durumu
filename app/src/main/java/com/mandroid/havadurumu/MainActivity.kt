package com.mandroid.havadurumu

import Weather
import WeatherService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var weatherImageView: ImageView
    private lateinit var temperatureTextView: TextView
    private lateinit var weatherStateTextView: TextView

    private lateinit var progressLayout: RelativeLayout

    private val retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.open-meteo.com/v1/")
            .build()

    private fun changeWeatherIcon(weatherCode: Int) {
        when (weatherCode) {
            1, 2, 3 -> {
                weatherImageView.setImageResource(R.drawable.bi_cloud_sun)
                weatherStateTextView.setText(R.string.partial_cloud)
                // set weather icon to imageview R.id.weather_icon

            }

            61, 63, 65, 66, 67, 80, 81, 82 -> {
                weatherImageView.setImageResource(R.drawable.bi_cloud_rain)
                weatherStateTextView.setText(R.string.rainy)
            }

            else -> {
                weatherImageView.setImageResource(R.drawable.sun)
                weatherStateTextView.setText(R.string.sunny)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherImageView = findViewById(R.id.weather_icon)
        temperatureTextView = findViewById(R.id.temperature)
        weatherStateTextView = findViewById(R.id.weather_state)

        progressLayout = findViewById(R.id.progress_layout)

        val weatherService = retrofit.create(WeatherService::class.java)
        weatherService.getWeather().enqueue(object : Callback<Weather> {
            override fun onResponse(
                call: retrofit2.Call<Weather>, response: retrofit2.Response<Weather>
            ) {
                val weather = response.body()
                if (weather != null) {
                    val text = getText(R.string.temperature)
                    temperatureTextView.text = String.format(text.toString(), weather.current.temperature2m.toInt())
                    changeWeatherIcon(weather.current.weatherCode)
                    progressLayout.visibility = RelativeLayout.GONE
                }
            }

            override fun onFailure(call: retrofit2.Call<Weather>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }
}