package ru.ispring.weather_lib.data

import com.google.gson.annotations.SerializedName

data class ResponseJson(
    @SerializedName("current")
    val current: CurrentWeatherJson,
    @SerializedName("forecast")
    val forecast: ForecastJson
)

data class CurrentWeatherJson(
    @SerializedName("temp_c")
    val temperature: Double,
    @SerializedName("condition")
    val condition: ConditionJson
)

data class ConditionJson(
    @SerializedName("code")
    val code: Int
)

data class ForecastJson(
    @SerializedName("forecastday")
    val forecastDay: List<ForecastDayJson>
)

data class ForecastDayJson(
    @SerializedName("day")
    val day: DayJson,
    @SerializedName("astro")
    val astronomy: AstronomyJson,
    @SerializedName("hour")
    val hour: List<HourJson>
)

data class DayJson(
    @SerializedName("condition")
    val condition: ConditionJson,
)

data class AstronomyJson(
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String
)

data class HourJson(
    @SerializedName("temp_c")
    val temperature: Double,
    @SerializedName("is_day")
    val isDay: Int
)