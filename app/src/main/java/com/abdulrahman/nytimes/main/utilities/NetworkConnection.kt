package com.abdulrahman.nytimes.main.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkConnection(private val context: Context) {

    @Suppress("DEPRECATION")
    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}