package com.rnaf.tambalbanonline.data.response

import com.rnaf.tambalbanonline.data.model.nearby.ModelResults
import com.google.gson.annotations.SerializedName

class ModelResultNearby {
    @SerializedName("results")
    lateinit var modelResults: List<ModelResults>
}