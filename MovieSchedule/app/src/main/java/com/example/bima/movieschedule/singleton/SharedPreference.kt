package com.example.bima.pariwisatayogyakarte.helper

import android.content.Context

class SharedPreference(context: Context) {

    private val pref = "SharedPref"
    private val firstTime = "firstTime"


    val preferences = context.getSharedPreferences(pref,Context.MODE_PRIVATE)

    fun firstTimeApp():Int{
        return preferences.getInt(firstTime,0)
    }

    fun setfirstTimeApp(n: Int){
        val editor = preferences.edit()
        editor.putInt(firstTime,n)
        editor.apply()
    }

}