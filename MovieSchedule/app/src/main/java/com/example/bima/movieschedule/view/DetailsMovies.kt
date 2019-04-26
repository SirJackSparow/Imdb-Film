package com.example.bima.movieschedule.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bima.movieschedule.BuildConfig
import com.example.bima.movieschedule.R
import com.example.bima.movieschedule.model.DetailsModel
import com.example.bima.movieschedule.presenter.Presenter
import com.example.bima.movieschedule.service.GetData
import com.example.bima.movieschedule.service.ServicesApi
import com.example.bima.movieschedule.view.`interface`.GetDatas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_movies.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import retrofit2.Call

class DetailsMovies : AppCompatActivity(), GetDatas{
    private lateinit var retrofits:GetData
    private lateinit var getDatas: Call<DetailsModel>
    var poster = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_movies)
        val intent = intent
        retrofits = ServicesApi.retrofit().create(GetData::class.java)
        getDatas = retrofits.detailsData(intent.getStringExtra("imdbID"),BuildConfig.ApiKey)
        var presenter = Presenter(this)
        presenter.detailsData(getDatas)
    }

    fun waShare(view: View){
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"

           val uriString = "ImDb poster url : $poster"

            intent.putExtra(Intent.EXTRA_TEXT,uriString)
            intent.`package` = "com.whatsapp"
            startActivity(intent)
        }catch (e : Exception){
            ctx.toast("WhatsApp Belum terinstall")
        }
    }

    fun fbshare(view: View){
        if (checkFacebookPackage(this@DetailsMovies,"com.facebook.katana")){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"

            val uriStrings = "ImDb poster url : $poster"

            intent.putExtra(Intent.EXTRA_TEXT,uriStrings)
            intent.`package` = "com.facebook.katana"

            startActivity(intent)
        }else{
            ctx.toast("Facebook Belum Terinstall")
        }

    }

    fun checkFacebookPackage(context: Context,target: String):Boolean{
        val pm = context.packageManager
        try {
            pm.getPackageInfo(target,PackageManager.GET_META_DATA)
        }catch (e : PackageManager.NameNotFoundException){
            return false
        }
        return true
    }

    override fun details(data: DetailsModel) {
          if(data.Response == "True"){
              if (data.Poster ==  "N/A"){
                  gbr.setImageResource(R.drawable.logo)
              }else{
                  Picasso.get().load(data.Poster).into(gbr)
              }

              poster = data.Poster.toString()
              tite.text = data.Title
              type.text = data.Type
              genre.text = data.Genre
              actor.text = "${data.Actors} (Actors)"
              director.text = "${data.Director} (director)"
              year.text = data.Year
              runtime.text = data.Runtime
              rated.text = data.Rated
          }
    }
}
