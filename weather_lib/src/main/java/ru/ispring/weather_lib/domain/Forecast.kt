package ru.ispring.weather_lib.domain

data class Forecast(
    val title: String,
    val precipitation: Precipitation,
    val info: String
)