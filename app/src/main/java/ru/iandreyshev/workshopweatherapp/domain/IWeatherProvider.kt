package ru.iandreyshev.workshopweatherapp.domain

import kotlinx.coroutines.flow.Flow

interface IWeatherProvider {
    fun getWeather(): Flow<Weather>
    fun addLocation(location: Location)
    fun nextLocation()
}