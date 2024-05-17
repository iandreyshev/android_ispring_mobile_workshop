package ru.ispring.weather_lib.domain

data class Weather(
    val location: String, // Йошкар-Ола, Россия
    val titleText: String, // 👋 Доброе утро! / 👋 Добный день / 👋 Добрый вечер
    val temperature: String, // Большая цифра по центру с символом температуры
    val precipitation: Precipitation, // Вид осадков
    val date: String, // Дата + время: Воскресенье 25 мая | 09:25
    val sunrise: String,
    val sunset: String,
    val forecastDay1: Forecast,
    val forecastNight1: Forecast,
    val forecastDay2: Forecast,
    val forecastNight2: Forecast,
) {

    val precText: String
        get() = when (precipitation) {
            Precipitation.RAIN -> "ДОЖДЬ"
            Precipitation.THUNDERSTORM -> "ГРОЗА"
            Precipitation.HAIL -> "ГРАД"
            Precipitation.CLOUDY -> "ПАСМУРНО"
            Precipitation.SUNNY -> "ЯСНО"
            Precipitation.CLOUDY_AND_SUNNY -> "ОБЛАЧНО"
            Precipitation.SNOW -> "СНЕГ"
        }

}