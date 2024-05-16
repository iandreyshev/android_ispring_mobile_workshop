package ru.iandreyshev.workshopweatherapp.domain

data class Forecast(
    val title: String,
    val precipitation: Precipitation,
    val info: String
)