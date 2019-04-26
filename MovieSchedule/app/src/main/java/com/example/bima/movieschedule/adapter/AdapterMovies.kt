package com.example.bima.movieschedule.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bima.movieschedule.R
import com.example.bima.movieschedule.model.ModelList
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class AdapterMovies(private val dataList:List<ModelList>,private val listener: (ModelList)->Unit)
    :RecyclerView.Adapter<MyHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bindItem(dataList[position],listener)
    }

}

class MyHolder(view: View): RecyclerView.ViewHolder(view){

    val year : TextView = view.find(R.id.year)
    val img : ImageView = view.find(R.id.gambar)
    val title: TextView = view.find(R.id.title)
    val type : TextView = view.find(R.id.type)

    @SuppressLint("SetTextI18n")
    fun bindItem(data: ModelList, listener: (ModelList) -> Unit){
        if(data.Poster == "N/A"){
            img.setImageResource(R.drawable.logo)
        }else if (data.Poster == null){
           img.setImageResource(R.drawable.logo)
        }else{
            Picasso.get().load(data.Poster).into(img)
        }
        type.text = data.Type
      title.text = "Title : ${data.Title}"
      year.text = data.Year
        itemView.setOnClickListener {
            listener(data)
        }
    }
}