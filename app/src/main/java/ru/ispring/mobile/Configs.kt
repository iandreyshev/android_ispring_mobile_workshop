package ru.ispring.mobile

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import ru.ispring.weather_lib.data.WeatherGateway
import ru.ispring.weather_lib.di.getViewModel
import ru.ispring.weather_lib.domain.DataMapper
import ru.ispring.weather_lib.domain.Forecast
import ru.ispring.weather_lib.domain.NetworkStateProvider
import ru.ispring.weather_lib.domain.Precipitation
import ru.ispring.weather_lib.domain.WeatherProviderImpl
import ru.ispring.weather_lib.presentation.WeatherViewModel

fun MainActivity.getWeatherViewModel() = lazy {
    getViewModel {
        WeatherViewModel(
            weatherProvider = WeatherProviderImpl(
                gateway = WeatherGateway(
                    networkStateProvider = NetworkStateProvider(this)
                ),
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

var ImageView.resourse: Int
    get() = 0
    set(value) {
        setImageResource(value)
    }


val Forecast.precImage: Int
    get() = when (precipitation) {
        Precipitation.RAIN -> R.drawable.img_rain
        Precipitation.THUNDERSTORM -> R.drawable.img_thunderstorm
        Precipitation.HAIL -> R.drawable.img_hail
        Precipitation.CLOUDY -> R.drawable.img_cloudy
        Precipitation.SUNNY -> R.drawable.img_sunny
        Precipitation.CLOUDY_AND_SUNNY -> R.drawable.img_cloudy_and_sunny
        Precipitation.SNOW -> R.drawable.img_snow
    }