package ru.ispring.weather_lib.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.ispring.weather_lib.data.ApiResult
import ru.ispring.weather_lib.data.WeatherGateway

class WeatherProviderImpl(
    private val gateway: WeatherGateway,
    private val dataMapper: DataMapper,
) : IWeatherProvider {
    private lateinit var mScope: CoroutineScope

    private val mLocations = mutableListOf<Location>()
    private var mCurrentLocation: Int = 0

    private var mCachedWeather: Weather? = null
    private var mCallRefresh = MutableSharedFlow<Int>()

    fun startRefresh(scope: CoroutineScope) {
        this.mScope = scope
        this.mScope.launch {
            IntProgression.fromClosedRange(1, MAX_UPDATES, 1).forEach {
                mCallRefresh.emit(it)
                delay(UPDATE_DELAY)
            }
        }
    }

    override fun getWeather(): Flow<Weather> {
        return mCallRefresh.map {
            val location = mLocations.getOrNull(mCurrentLocation)
                ?: return@map mCachedWeather

            when (val result = gateway.getWeatherData(location)) {
                is ApiResult.Success -> {
                    mCachedWeather = dataMapper.mapWeather(location, result.data)
                }

                else -> Unit
            }

            mCachedWeather
        }.flowOn(Dispatchers.IO)
            .filterNotNull()
    }

    override fun addLocations(vararg location: Location) {
        mLocations += location
        mScope.launch {
            mCallRefresh.emit(0)
        }
    }

    override fun nextLocation() {
        if (mCurrentLocation == mLocations.lastIndex) {
            mCurrentLocation = 0
        } else {
            mCurrentLocation++
        }

        mScope.launch {
            mCallRefresh.emit(0)
        }
    }

    companion object {
        private const val MAX_UPDATES = 500
        private const val UPDATE_DELAY = 60 * 1000L
    }

}