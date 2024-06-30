package ru.ispring.weather_lib.data

import com.androidnetworking.AndroidNetworking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ispring.weather_lib.domain.Location
import ru.ispring.weather_lib.domain.NetworkStateProvider

class WeatherGateway(
    private val networkStateProvider: NetworkStateProvider
) {

    suspend fun getWeatherData(location: Location): ApiResult<ResponseJson> {
        if (!networkStateProvider.isInternetConnected()) {
            return ApiResult.Error(ErrorType.NoInternet)
        }

        return withContext(Dispatchers.IO) {
            try {
                val request = AndroidNetworking
                    .get("https://api.weatherapi.com/v1/forecast.json")
                    .addQueryParameter(
                        mapOf(
                            "q" to "${location.latitude},${location.longitude}",
                            "key" to WEATHER_API_KEY,
                            "days" to "3"
                        )
                    ).build()

                val response = request.executeForObject(ResponseJson::class.java)

                when {
                    response.isSuccess -> ApiResult.Success(response.result as ResponseJson)
                    else -> ApiResult.Error(ErrorType.ServerConnectionError)
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
                ApiResult.Error(ErrorType.Unknown)
            }
        }
    }

    companion object {
        private const val WEATHER_API_KEY = "8ee146fceb3f41b6867101428242406"
    }

}