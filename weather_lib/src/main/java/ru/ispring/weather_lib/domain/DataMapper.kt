package ru.ispring.weather_lib.domain

import android.content.Context
import ru.ispring.weather_lib.R
import ru.ispring.weather_lib.data.DayForecastJson
import ru.ispring.weather_lib.data.ResponseJson
import ru.ispring.weather_lib.data.WeatherConditionJson
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class DataMapper(private val context: Context) {

    fun mapWeather(location: Location, json: ResponseJson): Weather {
        val currentDate = ZonedDateTime.now()
        val currentHour = currentDate.hour

        val title = when (currentHour) {
            in 5..10 -> context.getString(R.string.title_morning)
            in 11..17 -> context.getString(R.string.title_day)
            else -> context.getString(R.string.title_evening)
        }

        val todayForecast = json.data.weatherByPoint.forecast.days[0]
        val tomorrowForecast = json.data.weatherByPoint.forecast.days[1]
        val dayAfterTomorrowForecast = json.data.weatherByPoint.forecast.days[2]

        return Weather(
            location = location.title,
            title = title,
            temperature = "${json.data.weatherByPoint.weatherNow.temperature}°C",
            precipitation = json.data.weatherByPoint.weatherNow.condition.mapCondition(),
            date = mapDateTimeFull(currentDate),
            sunrise = mapTime(todayForecast.sunriseTime),
            sunset = mapTime(todayForecast.sunsetTime),
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

    private fun WeatherConditionJson.mapCondition(): Precipitation = when (this) {
        WeatherConditionJson.CLEAR -> Precipitation.SUNNY
        WeatherConditionJson.PARTLY_CLOUDY -> Precipitation.CLOUDY_AND_SUNNY
        WeatherConditionJson.CLOUDY -> Precipitation.CLOUDY

        WeatherConditionJson.OVERCAST,
        WeatherConditionJson.LIGHT_RAIN,
        WeatherConditionJson.RAIN,
        WeatherConditionJson.HEAVY_RAIN,
        WeatherConditionJson.SHOWERS -> Precipitation.RAIN

        WeatherConditionJson.SLEET,
        WeatherConditionJson.LIGHT_SNOW,
        WeatherConditionJson.SNOW,
        WeatherConditionJson.SNOWFALL -> Precipitation.SNOW

        WeatherConditionJson.HAIL -> Precipitation.HAIL

        WeatherConditionJson.THUNDERSTORM,
        WeatherConditionJson.THUNDERSTORM_WITH_RAIN,
        WeatherConditionJson.THUNDERSTORM_WITH_HAIL -> Precipitation.THUNDERSTORM
    }

    private fun mapForecast(
        json: DayForecastJson,
        date: ZonedDateTime,
        isDay: Boolean
    ) = Forecast(
        title = mapDateTimeShort(date),
        precipitation = when {
            isDay -> json.summary.day.condition.mapCondition()
            else -> json.summary.night.condition.mapCondition()
        },
        info = when {
            isDay -> json.summary.day.avgTemperature to R.string.day_temperature_template
            else -> json.summary.night.avgTemperature to R.string.night_temperature_template
        }.let { (temp, strRes) ->
            context.getString(strRes).replace("{temp}", "$temp°C")
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
        val date = ZonedDateTime.parse(strTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        return mapTime(date)
    }

    private fun mapTime(date: ZonedDateTime) =
        "${date.hour.toString().padStart(2, '0')}:${date.minute.toString().padStart(2, '0')}"

}