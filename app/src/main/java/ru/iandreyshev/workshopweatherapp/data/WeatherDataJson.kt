package ru.iandreyshev.workshopweatherapp.data

import com.google.gson.annotations.SerializedName

data class ResponseJson(
    @SerializedName("data")
    val data: WeatherDataJson
)

data class WeatherDataJson(
    @SerializedName("weatherByPoint")
    val weatherByPoint: WeatherByPointJson
)

data class WeatherByPointJson(
    @SerializedName("now")
    val weatherNow: WeatherNowJson,
    @SerializedName("forecast")
    val forecast: ForecastJson
)

data class WeatherNowJson(
    @SerializedName("temperature")
    val temperature: Int,
    @SerializedName("condition")
    val condition: WeatherConditionJson
)

data class ForecastJson(
    @SerializedName("days")
    val days: List<DayForecastJson>
)

data class DayForecastJson(
    @SerializedName("sunriseTime")
    val sunriseTime: String,
    @SerializedName("sunsetTime")
    val sunsetTime: String,
    @SerializedName("summary")
    val summary: DayForecastSummaryJson
)

data class DayForecastSummaryJson(
    @SerializedName("day")
    val day: DayForecastDayNightJson,
    @SerializedName("night")
    val night: DayForecastDayNightJson
)

data class DayForecastDayNightJson(
    @SerializedName("avgTemperature")
    val avgTemperature: Int,
    @SerializedName("condition")
    val condition: WeatherConditionJson
)

enum class WeatherConditionJson {
    @SerializedName("CLEAR")
    CLEAR,

    @SerializedName("PARTLY_CLOUDY")
    PARTLY_CLOUDY,

    @SerializedName("CLOUDY")
    CLOUDY,

    @SerializedName("OVERCAST")
    OVERCAST,

    @SerializedName("LIGHT_RAIN")
    LIGHT_RAIN,

    @SerializedName("RAIN")
    RAIN,

    @SerializedName("HEAVY_RAIN")
    HEAVY_RAIN,

    @SerializedName("SHOWERS")
    SHOWERS,

    @SerializedName("SLEET")
    SLEET,

    @SerializedName("LIGHT_SNOW")
    LIGHT_SNOW,

    @SerializedName("SNOW")
    SNOW,

    @SerializedName("SNOWFALL")
    SNOWFALL,

    @SerializedName("HAIL")
    HAIL,

    @SerializedName("THUNDERSTORM")
    THUNDERSTORM,

    @SerializedName("THUNDERSTORM_WITH_RAIN")
    THUNDERSTORM_WITH_RAIN,

    @SerializedName("THUNDERSTORM_WITH_HAIL")
    THUNDERSTORM_WITH_HAIL

}