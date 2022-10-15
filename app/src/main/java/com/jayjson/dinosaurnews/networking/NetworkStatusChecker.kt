package com.jayjson.dinosaurnews.networking

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

/**
 * Checks the Internet connection and performs an action if it's active.
 */
@RequiresApi(Build.VERSION_CODES.M)
class NetworkStatusChecker(private val connectivityManager: ConnectivityManager?) {

    inline fun performIfConnectedToInternet(action: () -> Unit) {
        if (hasInternetConnection()) {
            action()
        } else {
            Log.i(TAG, "Not executing input action due to having no internet connection")
        }
    }

    fun hasInternetConnection(): Boolean {
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }

    inline fun performIfConnectedToWiFi(wifiOnlyOn: Boolean, action: () -> Unit) {
        if (wifiOnlyOn) {
            if (hasWifiConnection()) {
                action()
            } else {
                Log.i(TAG, "Not executing input action due to having no WiFi connection")
            }
        } else {
            action()
        }
    }

    fun hasWifiConnection(): Boolean {
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    companion object {
        const val TAG = "NetworkStatusChecker"
    }
}
