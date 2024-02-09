package com.example.taskftc.domain.useCase

import android.content.Context
import android.net.ConnectivityManager

class CheckInternetUserCase {
    fun execute(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @Suppress("DEPRECATION") val netInfo = cm.activeNetworkInfo
        @Suppress("DEPRECATION")
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}