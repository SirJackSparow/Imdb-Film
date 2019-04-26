package com.example.bima.movieschedule.view.`interface`

import com.example.bima.movieschedule.model.DetailsModel
import com.example.bima.movieschedule.model.ModelList

interface GetDatas {
    fun getData(data: List<ModelList>?,response: String){}
    fun details(data: DetailsModel){}
}