package com.rnaf.tambalbanonline.data.response

import com.rnaf.tambalbanonline.data.model.details.ModelDetail
import com.google.gson.annotations.SerializedName

class ModelResultDetail {
    @SerializedName("result")
    lateinit var modelDetail: ModelDetail
}