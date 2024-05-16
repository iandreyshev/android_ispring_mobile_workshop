package ru.iandreyshev.workshopweatherapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.iandreyshev.workshopweatherapp.domain.Weather
import ru.iandreyshev.workshopweatherapp.domain.WeatherProvider

class WeatherViewModel(
    weatherProvider: WeatherProvider
) : ViewModel() {

    val weatherData: LiveData<Weather>
        get() = mWeatherData

    private val mWeatherData = MutableLiveData<Weather>()

    init {
        weatherProvider.getWeather().onEach {
            mWeatherData.value = it
        }.launchIn(viewModelScope)
    }

}