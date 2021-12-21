package com.rnaf.tambalbanonline.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rnaf.tambalbanonline.R
import com.rnaf.tambalbanonline.data.model.nearby.ModelResults
import com.rnaf.tambalbanonline.view.activities.RuteActivity
import com.rnaf.tambalbanonline.view.adapter.MainAdapter.MainViewHolder
import kotlinx.android.synthetic.main.list_item_location.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter (private val context: Context): RecyclerView.Adapter<MainViewHolder>(){
    private val modelResultsArrayList = ArrayList<ModelResults>()

    fun setLocationAdapter(items: ArrayList<ModelResults>){
        modelResultsArrayList.clear()
        modelResultsArrayList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_item_location, p0, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(p0: MainViewHolder, p1: Int) {
        val modelResult = modelResultsArrayList[p1]

        // set rating
        val newValue = modelResult.rating.toFloat()
        p0.ratingBar.numStars = 5
        p0.ratingBar.stepSize = 0.5.toFloat()
        p0.ratingBar.rating = newValue
        p0.tvNamaJalan.text = modelResult.vicinity
        p0.tvNamaLokasi.text = modelResult.name
        p0.tvRating.text = "(" + modelResult.rating + ")"

        // set data to share & intent
        val strPlaceId = modelResultsArrayList[p1].placeId
        val strNamaLokasi = modelResultsArrayList[p1].name
        val strNamaJalan = modelResultsArrayList[p1].vicinity
        val strLat = modelResultsArrayList[p1].modelGeometry.modelLocation.lat
        val strLong = modelResultsArrayList[p1].modelGeometry.modelLocation.lng

        // send data to another actvity
        p0.linearRute.setOnClickListener{
            val intent = Intent(context, RuteActivity::class.java)
            intent.putExtra("placeId", strPlaceId)
            intent.putExtra("vicinity", strNamaJalan)
            intent.putExtra("lat", strLat)
            intent.putExtra("lng", strLong)
            context.startActivity(intent)
        }

        // intent to share location
        p0.imageShare.setOnClickListener{
            val strUrl = "http://maps.google.com/maps?saddr=$strLat,$strLong"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, strNamaLokasi)
            intent.putExtra(Intent.EXTRA_TEXT, strUrl)
            context.startActivity((Intent.createChooser(intent, "Bagikan :")))
        }
    }

    override fun getItemCount(): Int {
        return modelResultsArrayList.size
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var linearRute : LinearLayout
        var tvNamaJalan : TextView
        var tvNamaLokasi : TextView
        var tvRating : TextView
        var imageShare : ImageView
        var ratingBar : RatingBar

        init {
            linearRute = itemView.linearRute
            tvNamaJalan = itemView.tvNamajalan
            tvNamaLokasi = itemView.tvNamaLokasi
            tvRating = itemView.tvRating
            imageShare = itemView.imageShare
            ratingBar = itemView.ratingBar
        }

    }

}