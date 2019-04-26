package com.example.bima. pariwisatayogyakarte.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.content.ContextCompat.getSystemService


class CheckConnection {
    companion object {
        fun isNetworkOnline(context: Context): Boolean{
            var connected : Boolean = false
           val cm  = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
           val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null){
                //konek di internet
                connected = true
            }else if (networkInfo?.type == ConnectivityManager.TYPE_WIFI){
                connected = true
            }else if (networkInfo?.type == ConnectivityManager.TYPE_MOBILE){
                connected = true
            }else {
                false
            }
            return connected
        }
    }

}