package com.szareckii.map.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") var name: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("lat") val lat: Double = 0.0,
    @SerializedName("lng") val lng: Double = 0.0
) : Serializable

