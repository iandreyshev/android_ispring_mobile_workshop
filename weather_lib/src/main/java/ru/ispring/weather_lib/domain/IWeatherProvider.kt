package ru.ispring.weather_lib.domain

import kotlinx.coroutines.flow.Flow

interface IWeatherProvider {
    fun getWeather(): Flow<Weather>
    fun addLocations(vararg location: Location)
    fun nextLocation()
}