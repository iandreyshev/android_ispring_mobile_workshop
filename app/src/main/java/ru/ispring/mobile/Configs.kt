package ru.ispring.mobile

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
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

inline fun <T : ViewBinding> FragmentActivity.viewBindings(
    crossinline bind: (View) -> T
) = object : Lazy<T> {

    private var mCached: T? = null

    override val value: T
        get() = mCached ?: bind(
            findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        ).also {
            mCached = it
        }

    override fun isInitialized(): Boolean = mCached != null

}
