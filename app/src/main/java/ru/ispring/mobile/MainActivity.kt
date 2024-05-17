package ru.ispring.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ispring.mobile.databinding.ActivityMainBinding
import ru.ispring.weather_lib.domain.Location
import ru.ispring.weather_lib.domain.Precipitation
import ru.ispring.weather_lib.domain.Weather

class MainActivity : AppCompatActivity() {

    private val weatherProvider by getWeatherViewModel()
    private val screen by viewBindings(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherProvider.weatherData.observe(this, ::renderWeather)
        weatherProvider.addLocations(
            Location("Йошкар-Ола, Россия", 56.6318896, 47.8979313),
        )
    }

    private fun renderWeather(weather: Weather) {

    }

}
