package ru.ispring.weather_lib.data

import com.androidnetworking.AndroidNetworking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
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
            val query = getQuery(location.latitude, location.longitude)

            try {
                val request = AndroidNetworking.post("https://api.weather.yandex.ru/graphql/query")
                    .addHeaders(mapOf("X-Yandex-API-Key" to YANDEX_API_KEY))
                    .addJSONObjectBody(JSONObject().apply { put("query", query) }).build()

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

    private fun getQuery(lat: Double, lon: Double) = """
{
  weatherByPoint(request: { lat: $lat, lon: $lon }) {
    now {
      temperature
      condition
    },
    forecast {
      days(limit: 3) {
        sunriseTime
        sunsetTime
         summary {
          day {
            avgTemperature
            condition
          },
          night {
            avgTemperature
            condition
          }
        }
      }
    }
  }
}
        """

    companion object {
        private const val YANDEX_API_KEY = "5d2b9d55-95ff-483a-a40d-bec8e1cd6525"
    }

}