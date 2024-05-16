package ru.ispring.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ispring.weather_lib.domain.Weather

class MainActivity : AppCompatActivity() {

    private val mViewModel by getWeatherViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel.weatherData.observe(this, ::renderWeather)
    }

    private fun renderWeather(weather: Weather) {
        println(weather)
    }

}
