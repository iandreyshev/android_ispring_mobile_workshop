package ru.iandreyshev.workshopweatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.iandreyshev.workshopweatherapp.R
import ru.iandreyshev.workshopweatherapp.di.getWeatherViewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel by getWeatherViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel.weatherData.observe(this) { weather ->
            println(weather)
        }
    }

}
