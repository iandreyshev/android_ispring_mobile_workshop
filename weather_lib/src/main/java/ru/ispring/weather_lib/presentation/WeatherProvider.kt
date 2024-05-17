package ru.ispring.weather_lib.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ispring.weather_lib.domain.Location
import ru.ispring.weather_lib.domain.Weather
import ru.ispring.weather_lib.domain.WeatherProviderImpl

class WeatherProvider(
    private val weatherProvider: WeatherProviderImpl
) : ViewModel() {

    val weatherData: LiveData<Weather>
        get() = mWeatherData

    private val mWeatherData = MutableLiveData<Weather>()

    init {
        weatherProvider.startRefresh(viewModelScope)
        weatherProvider.getWeather()
            .onEach { mWeatherData.value = it }
            .launchIn(viewModelScope)
    }

    fun addLocations(vararg location: Location) {
        weatherProvider.addLocations(*location)
    }

    fun nextLocation() {
        weatherProvider.nextLocation()
    }

}