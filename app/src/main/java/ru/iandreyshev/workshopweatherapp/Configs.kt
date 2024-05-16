package ru.iandreyshev.workshopweatherapp

import ru.iandreyshev.weather_lib.data.WeatherGateway
import ru.iandreyshev.weather_lib.di.getViewModel
import ru.iandreyshev.weather_lib.domain.DataMapper
import ru.iandreyshev.weather_lib.domain.LocationProvider
import ru.iandreyshev.weather_lib.domain.NetworkStateProvider
import ru.iandreyshev.weather_lib.domain.WeatherProvider
import ru.iandreyshev.weather_lib.presentation.WeatherViewModel

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
