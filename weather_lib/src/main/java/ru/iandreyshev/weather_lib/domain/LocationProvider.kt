package ru.iandreyshev.weather_lib.domain

class LocationProvider {

    private val mLocations = listOf(
        Location(
            title = "Йошкар-Ола, Россия",
            latitude = 56.6318896,
            longitude = 47.8979313
        )
    )

    fun getLocation(): Location {
        return mLocations[0]
    }

}