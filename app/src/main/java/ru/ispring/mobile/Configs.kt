package ru.ispring.mobile

import ru.ispring.weather_lib.data.WeatherGateway
import ru.ispring.weather_lib.di.getViewModel
import ru.ispring.weather_lib.domain.DataMapper
import ru.ispring.weather_lib.domain.LocationProvider
import ru.ispring.weather_lib.domain.NetworkStateProvider
import ru.ispring.weather_lib.domain.WeatherProvider
import ru.ispring.weather_lib.presentation.WeatherViewModel

fun MainActivity.getWeatherViewModel() = lazy {
    getViewModel {
        WeatherViewModel(
            weatherProvider = WeatherProvider(
                gateway = WeatherGateway(
                    networkStateProvider = NetworkStateProvider(this)
                ),
                locationProvider = LocationProvider(),
                dataMapper = DataMapper(this)
            )
        )
    }
}
