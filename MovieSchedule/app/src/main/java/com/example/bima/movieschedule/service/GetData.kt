package com.example.bima.movieschedule.service

import com.example.bima.movieschedule.model.DataModel
import com.example.bima.movieschedule.model.DetailsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetData {

    @GET("/")
    fun getData(@Query("type") type: String,
                @Query("s") s: String,
                @Query("page") page: String,
                @Query("y") y: String,
                @Query("apikey") apikey: String): Call<DataModel>

    @GET("/")
    fun detailsData(@Query("i") id :  String,
                    @Query("apikey") apikey: String):Call<DetailsModel>
}