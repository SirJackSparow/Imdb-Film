package com.example.bima.movieschedule.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServicesApi {
    companion object {
        fun retrofit () : Retrofit{
            var retrofit = Retrofit.Builder()
                        .baseUrl( "http://www.omdbapi.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            return retrofit
        }

    }
}