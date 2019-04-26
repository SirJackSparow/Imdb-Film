package com.example.bima.movieschedule.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.widget.ArrayAdapter
import com.example.bima.movieschedule.BuildConfig
import com.example.bima.movieschedule.R
import com.example.bima.movieschedule.adapter.AdapterMovies
import com.example.bima.movieschedule.model.DataModel
import com.example.bima.movieschedule.model.ModelList
import com.example.bima.movieschedule.presenter.Presenter
import com.example.bima.movieschedule.service.GetData
import com.example.bima.movieschedule.service.ServicesApi
import com.example.bima.movieschedule.view.`interface`.GetDatas
import kotlinx.android.synthetic.main.activity_searching.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call

class Searching : AppCompatActivity(), GetDatas {

    private lateinit var adapter: AdapterMovies
    private lateinit var retrofits: GetData
    private lateinit var getDatas: Call<DataModel>
    private lateinit var presenter:Presenter
    private lateinit var mLayoutManager: LinearLayoutManager
    private var datas: MutableList<ModelList>? = mutableListOf()
    var load: Boolean = true
    var searching : String? = "Batman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching)

        val suggestion = mutableListOf("Batman","Spiderman","dracula","stackoverflow",
            "antman","robin","avenger","captain","amerika","zoro","wall","f","g","hero","incredible","baby","omar","Prank","Queen")
        retrofits = ServicesApi.retrofit().create(GetData::class.java)
        adapter = AdapterMovies(datas!!){ctx.startActivity<DetailsMovies>("imdbID" to it.imdbID)}
        presenter = Presenter(this)
        item_re.adapter = adapter
        item_re.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var visibleItems = mLayoutManager.childCount
                var totalItemCount = mLayoutManager.itemCount
                var pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
                if (dy > 0){
                    if (load){
                        if ((visibleItems + pastVisibleItems)>= totalItemCount) {
                            //add data to infinite
                            getDatas = retrofits.getData("movie", searching!!, "", "", BuildConfig.ApiKey)
                            presenter.getData(getDatas)
                            adapter = AdapterMovies(datas!!){ ctx.startActivity<DetailsMovies>("imdbID" to it.imdbID)}
                            item_re.adapter = adapter
                        }
                    }
                }
            }
        })
        mLayoutManager = LinearLayoutManager(this)
        item_re.layoutManager = mLayoutManager
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searching = p0
                getDatas = retrofits.getData("",p0!!,"","",BuildConfig.ApiKey)
                presenter.getData(getDatas)
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                searching = p0
                getDatas = retrofits.getData("",p0!!,"","",BuildConfig.ApiKey)
                presenter.getData(getDatas)
                return false
            }
        })

    }

    override fun getData(data: List<ModelList>?, response: String) {
        if (response == "True"){
            datas?.clear()
            datas?.addAll(data!!)
            adapter.notifyDataSetChanged()
        }
    }
}
