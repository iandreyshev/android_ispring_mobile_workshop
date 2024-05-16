package ru.iandreyshev.workshopweatherapp.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.iandreyshev.workshopweatherapp.data.WeatherGateway
import ru.iandreyshev.workshopweatherapp.domain.DataMapper
import ru.iandreyshev.workshopweatherapp.domain.LocationProvider
import ru.iandreyshev.workshopweatherapp.domain.NetworkStateProvider
import ru.iandreyshev.workshopweatherapp.domain.WeatherProvider
import ru.iandreyshev.workshopweatherapp.presentation.WeatherViewModel
import ru.iandreyshev.workshopweatherapp.ui.MainActivity

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

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.NewInstanceFactory() {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory)[T::class.java]
}