package com.example.bima.movieschedule.presenter

import android.content.ContentValues.TAG
import android.util.Log
import com.example.bima.movieschedule.model.DataModel
import com.example.bima.movieschedule.model.DetailsModel
import com.example.bima.movieschedule.view.`interface`.GetDatas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Presenter (private val view: GetDatas){
    fun getData(getData: Call<DataModel>){
        getData.enqueue(object : Callback<DataModel>{
            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                Log.d(TAG,"Gagal")
            }

            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
               val responses = response.body()?.Response
                val data = response.body()?.Search
                view.getData(data, responses!!)
            }
        })
    }

    fun detailsData(getDetails :  Call<DetailsModel>){
        getDetails.enqueue(object : Callback<DetailsModel>{
            override fun onFailure(call: Call<DetailsModel>, t: Throwable) {
                Log.d(TAG,"Gagal")
            }
            override fun onResponse(call: Call<DetailsModel>, response: Response<DetailsModel>) {
                val data = response.body()
                view.details(data!!)

            }

        })
    }

}