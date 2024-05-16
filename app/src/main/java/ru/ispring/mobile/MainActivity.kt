package ru.ispring.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ispring.mobile.databinding.ActivityMainBinding
import ru.ispring.weather_lib.domain.Weather

class MainActivity : AppCompatActivity() {

    private val viewModel by getWeatherViewModel()
    private val binding by viewBindings(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.weatherData.observe(this, ::renderWeather)
    }

    private fun renderWeather(weather: Weather) {

    }

}
