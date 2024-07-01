package ru.ispring.weather_lib.domain

import android.content.Context
import ru.ispring.weather_lib.R
import ru.ispring.weather_lib.data.ForecastDayJson
import ru.ispring.weather_lib.data.ResponseJson
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

class DataMapper(private val context: Context) {

    fun mapWeather(location: Location, json: ResponseJson): Weather {
        val currentDate = ZonedDateTime.now()
        val currentHour = currentDate.hour

        val title = when (currentHour) {
            in 5..10 -> context.getString(R.string.title_morning)
            in 11..17 -> context.getString(R.string.title_day)
            else -> context.getString(R.string.title_evening)
        }

        val todayForecast = json.forecast.forecastDay[0]
        val tomorrowForecast = json.forecast.forecastDay[1]
        val dayAfterTomorrowForecast = json.forecast.forecastDay[2]

        return Weather(
            location = location.title,
            title = title,
            temperature = "${json.current.temperature.roundToInt()}°C",
            precipitation = json.current.condition.code.toPrecipitation(),
            date = mapDateTimeFull(currentDate),
            sunrise = mapTime(todayForecast.astronomy.sunrise),
            sunset = mapTime(todayForecast.astronomy.sunset),
            forecastDay1 = mapForecast(
                json = tomorrowForecast,
                date = currentDate.plusDays(1),
                isDay = true
            ),
            forecastNight1 = mapForecast(
                json = tomorrowForecast,
                date = currentDate.plusDays(1),
                isDay = false
            ),
            forecastDay2 = mapForecast(
                json = dayAfterTomorrowForecast,
                date = currentDate.plusDays(2),
                isDay = true
            ),
            forecastNight2 = mapForecast(
                json = dayAfterTomorrowForecast,
                date = currentDate.plusDays(2),
                isDay = false
            )
        )
    }

    private fun Int.toPrecipitation(): Precipitation = when (this) {
        1000 -> Precipitation.SUNNY
        1003 -> Precipitation.CLOUDY_AND_SUNNY
        1006 -> Precipitation.CLOUDY

        1072, 1168, 1171, 1198, 1201 -> Precipitation.HAIL

        1180, 1183, 1186, 1189, 1192, 1195, 1204, 1207,
        1240, 1243, 1246, 1249, 1252, 1255, 1258, 1261,
        1264, 1009, 1063, 1150, 1153 -> Precipitation.RAIN

        1210, 1213, 1216, 1219, 1222, 1225, 1237, 1066,
        1069, 1114, 1117 -> Precipitation.SNOW

        1273, 1276, 1279, 1282, 1087 -> Precipitation.THUNDERSTORM

        else -> Precipitation.CLOUDY
    }

    private fun mapForecast(
        json: ForecastDayJson,
        date: ZonedDateTime,
        isDay: Boolean
    ) = Forecast(
        title = mapDateTimeShort(date),
        precipitation = json.day.condition.code.toPrecipitation(),
        info = run {
            val dayAvgTemp = json.hour.filter { it.isDay == 1 }.let { list ->
                (list.sumOf { it.temperature } / list.size).roundToInt()
            }
            val nightAvgTemp = json.hour.filter { it.isDay == 0 }.let { list ->
                (list.sumOf { it.temperature } / list.size).roundToInt()
            }

            when {
                isDay -> dayAvgTemp to R.string.day_temperature_template
                else -> nightAvgTemp to R.string.night_temperature_template
            }.let { (temp, strRes) ->
                context.getString(strRes).replace("{temp}", "$temp°C")
            }
        }
    )

    private fun mapDateTimeFull(dateTime: ZonedDateTime): String {
        val dayOfWeek = dateTime.dayOfWeek
            .getDisplayName(TextStyle.FULL, Locale.getDefault())
            .replaceFirstChar { it.uppercase() }

        val date = dateTime.format(DateTimeFormatter.ofPattern("dd MMM", Locale.getDefault()))
        val time = mapTime(dateTime)

        return "$dayOfWeek $date | $time"
    }

    private fun mapDateTimeShort(dateTime: ZonedDateTime): String {
        val dayOfWeek = dateTime.dayOfWeek
            .getDisplayName(TextStyle.SHORT, Locale.getDefault())
            .replaceFirstChar { it.uppercase() }

        val date = dateTime.format(DateTimeFormatter.ofPattern("dd MMM", Locale.getDefault()))

        return "$dayOfWeek $date"
    }

    private fun mapTime(strTime: String): String {
        val time = LocalTime.parse(strTime, DateTimeFormatter.ofPattern("hh:mm a"))
        return time.toString()
    }

    private fun mapTime(date: ZonedDateTime) =
        "${date.hour.toString().padStart(2, '0')}:${date.minute.toString().padStart(2, '0')}"

}