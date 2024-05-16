package ru.ispring.weather_lib.domain

data class Weather(
    val location: String, // Йошкар-Ола, Россия
    val title: String, // 👋 Доброе утро! / 👋 Добный день / 👋 Добрый вечер
    val temperature: String, // Большая цифра по центру с символом температуры
    val precipitation: Precipitation, // Вид осадков
    val date: String, // Дата + время: Воскресенье 25 мая | 09:25
    val sunrise: String,
    val sunset: String,
    val forecastDay1: Forecast,
    val forecastNight1: Forecast,
    val forecastDay2: Forecast,
    val forecastNight2: Forecast,
)