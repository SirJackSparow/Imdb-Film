package com.example.bima.movieschedule.view

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.elconfidencial.bubbleshowcase.BubbleShowCase
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence
import com.example.bima.movieschedule.BuildConfig
import com.example.bima.movieschedule.R
import com.example.bima.movieschedule.adapter.AdapterMovies
import com.example.bima.movieschedule.model.DataModel
import com.example.bima.movieschedule.model.ModelList
import com.example.bima.movieschedule.presenter.Presenter
import com.example.bima.movieschedule.service.GetData
import com.example.bima.movieschedule.service.ServicesApi
import com.example.bima.movieschedule.view.`interface`.GetDatas
import com.example.bima.pariwisatayogyakarte.helper.BubleHelp
import com.example.bima.pariwisatayogyakarte.helper.SharedPreference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call

class MainActivity : AppCompatActivity() , GetDatas {
    private lateinit var adapter: AdapterMovies
    private lateinit var retrofits: GetData
    private lateinit var getDatas: Call<DataModel>
    private var datas: MutableList<ModelList>? = mutableListOf()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var buble: BubbleShowCaseBuilder
    var load: Boolean = true
    var years : String = ""

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkFistime()

         val page = arrayListOf("Page","1","2","3")
        val arrayPage = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,page)
        spinner3.adapter = arrayPage

        spinner3.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (page[position]== "Page"){
                    getDatas = retrofits.getData("movie","Batman","1",years,BuildConfig.ApiKey)
                    presenter.getData(getDatas)
                }else{
                    getDatas = retrofits.getData("movie","Batman",page[position],years,BuildConfig.ApiKey)
                    presenter.getData(getDatas)
                }
                ctx.toast(page[position])
            }

        }
        search.setOnClickListener {
            ctx.startActivity<Searching>()
        }
        retrofits= ServicesApi.retrofit().create(GetData::class.java)
        getDatas = retrofits.getData("movie","Batman",spinner3.selectedItem.toString(),years,BuildConfig.ApiKey)
        presenter = Presenter(this)


        filter.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog,null)
            val dataType = arrayListOf("movie","series")
            val tahun = arrayListOf("selected year","1994","1995","1996","1997",
                "1998","1999","2000","2001","2002","2003","2004","2005","2006","2009","2010","2017","2018","2019")
            val tahunseries = arrayListOf("selected series years","1995-2001","2001-2009","2009-2010","2010-2017","2017-2019")
            dialogBuilder.setView(view)
            dialogBuilder.setTitle("Filter")

            val arrayType = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,dataType)
            val arrayYear = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,tahun)
            val arrayYearSeries = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,tahunseries)

            val spinner1 = view.findViewById<Spinner>(R.id.spinner1)
            val spinner2 = view.findViewById<Spinner>(R.id.spinner2)
            val spinner4 = view.findViewById<Spinner>(R.id.spinner4)
            spinner1?.adapter = arrayType
            spinner2?.adapter = arrayYear
            spinner4?.adapter = arrayYearSeries

            dialogBuilder.setPositiveButton("Filter"){
                    _: DialogInterface, _:Int ->

                when(spinner1.selectedItem.toString().trim()){
                    "movie".trim() -> {
                        if (spinner2.selectedItem.toString()=="selected year"){
                            this.years = ""
                        }else {
                            this.years = spinner2.selectedItem.toString()
                        }
                    }
                    "series".trim() -> {
                        if (spinner4.selectedItem.toString()=="selected series years"){
                            this.years = ""
                        }else{
                            this.years = spinner4.selectedItem.toString()
                        }
                    }
                    else -> ctx.toast("None")
                }


                getDatas = retrofits.getData(spinner1.selectedItem.toString(),"Batman",page.toString(),this.years,BuildConfig.ApiKey)
                presenter.getData(getDatas)
            }

            dialogBuilder.show()


        }

        item_res.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItems = mLayoutManager.childCount
                val totalItemCount = mLayoutManager.itemCount
                val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
                if (dy > 0){
                    if (load){
                        if ((visibleItems + pastVisibleItems)>= totalItemCount) {
                            //add data to infinite
                            getDatas = retrofits.getData("movie", "Batman", page.toString(), years, BuildConfig.ApiKey)
                            presenter.getData(getDatas)
                            adapter = AdapterMovies(datas!!){ ctx.startActivity<DetailsMovies>("imdbID" to it.imdbID)}
                            item_res.adapter = adapter
                        }
                    }
                }

            }
        })
        adapter = AdapterMovies(datas!!){ ctx.startActivity<DetailsMovies>("imdbID" to it.imdbID) }
        item_res.adapter = adapter
        mLayoutManager = LinearLayoutManager(this)
        item_res.layoutManager = mLayoutManager
    }

    fun checkFistime(){
        var pref = SharedPreference(this)
        if (pref.firstTimeApp() == 0){
            BubbleShowCaseSequence()
                .addShowCase(BubleHelp.getSimpleChase(this,"Tombol Searching", "Imdb").targetView(search))
                .addShowCase(BubleHelp.getSimpleChase(this,"Tombol Filter","Imdb").targetView(filter))
                .addShowCase(BubleHelp.getSimpleChase(this,"Click untuk Melihat Detail","Imdb")
                    .arrowPosition(BubbleShowCase.ArrowPosition.TOP).targetView(item_res))
                .addShowCase(BubleHelp.getSimpleChase(this,"Halaman","Imdb").targetView(spinner3))
                .show()
            pref.setfirstTimeApp(1)

        }
    }
    override fun getData(data: List<ModelList>?, response: String) {
        if (response == "True"){
            item_res.visibility = View.VISIBLE
            notfound.visibility = View.GONE
            datas?.clear()
            datas?.addAll(data!!)
            adapter.notifyDataSetChanged()
        }else{
            item_res.visibility = View.GONE
            notfound.visibility = View.VISIBLE
           ctx.toast("empty data !,Not Found")
        }
    }

}
