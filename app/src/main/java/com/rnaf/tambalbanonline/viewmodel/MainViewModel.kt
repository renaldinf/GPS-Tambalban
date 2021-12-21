package com.rnaf.tambalbanonline.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rnaf.tambalbanonline.data.model.details.ModelDetail
import com.rnaf.tambalbanonline.data.model.nearby.ModelResults
import com.rnaf.tambalbanonline.data.response.ModelResultDetail
import com.rnaf.tambalbanonline.data.response.ModelResultNearby
import com.rnaf.tambalbanonline.networking.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel{
    private val modelResultsMutableLiveData = MutableLiveData<ArrayList<ModelResults>>()
    private val modelDetailMutableLiveData = MutableLiveData<ModelDetail>()

    companion object{
        var strApiKey = "YOUR API KEY"
    }

    fun setMarkerLocation(strLocation : String){
        val apiService = ApiClient.getClient()
        val call = apiService. getDataResult(strApiKey, "Tambal Ban", strLocation, "distance")
        call.enqueue(object : Callback<ModelResultNearby>{
            override fun onResponse(call : Call<ModelResultNearby>, response: Response<ModelResultNearby>){
                if (!response.isSuccessful){
                    Log.e("response", response.toString())
                } else if (response.body() != null){
                    val items = ArrayList(response.body()!!.modelResults)
                    modelResultsMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ModelResultNearby?>, t: Throwable){
                Log.e("failure", t.toString())
            }
        })
    }

    fun setDetailLocation(strPlaceID : String){
        val apiService = ApiClient.getClient()
        val call = apiService.getDetailResult(strApiKey, strPlaceID)
        call.enqueue(object : Callback<ModelResultDetail>{
            override fun onResponse(call : Call<ModelResultDetail>, response: Response<ModelResultDetail>){
                if (!response.isSuccessful){
                    Log.e("response", response.toString())
                } else if (response.body() != null){
                    modelDetailMutableLiveData.postValue(response.body()!!.modelDetail)
                }
            }

            override fun onFailure(call: Call<ModelResultDetail?>, t: Throwable){
                Log.e("failure", t.toString())
            }
        })
    }

    fun getMarkerLocation() : LiveData<ArrayList<ModelResults>> = modelResultsMutableLiveData
    fun getDetailLocation() : LiveData<ModelDetail> = modelDetailMutableLiveData

}