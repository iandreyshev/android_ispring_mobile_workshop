package ru.iandreyshev.workshopweatherapp.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.iandreyshev.workshopweatherapp.data.ApiResult
import ru.iandreyshev.workshopweatherapp.data.WeatherGateway

class WeatherProvider(
    private val gateway: WeatherGateway,
    private val locationProvider: LocationProvider,
    private val dataMapper: DataMapper
) : IWeatherProvider {

    private var mCachedWeather: Weather? = null

    override fun getWeather(): Flow<Weather> {
        return flow {
            IntProgression.fromClosedRange(1, MAX_UPDATES, 1).forEach {
                emit(it)
                delay(UPDATE_DELAY)
            }
        }.map {
            val location = locationProvider.getLocation()

            when (val result = gateway.getWeatherData(location)) {
                is ApiResult.Success -> {
                    mCachedWeather = dataMapper.mapWeather(location, result.data)
                }

                else -> Unit
            }

            mCachedWeather
        }.flowOn(Dispatchers.IO).filterNotNull()
    }

    override fun addLocation(location: Location) {

    }

    override fun nextLocation() {

    }

    companion object {
        private const val MAX_UPDATES = 500
        private const val UPDATE_DELAY = 60 * 1000L
    }

}