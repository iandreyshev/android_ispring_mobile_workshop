package ru.ispring.weather_lib.domain

import android.content.Context
import android.net.ConnectivityManager

class NetworkStateProvider(context: Context) {

    private val mConnectivityManager = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    fun isInternetConnected(): Boolean = mConnectivityManager?.activeNetwork != null

}