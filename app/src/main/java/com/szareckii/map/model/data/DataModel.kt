package com.szareckii.map.model.data

import com.google.gson.annotations.SerializedName

class DataModel(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") var name: String = "",
    @field:SerializedName("description") var description: String = "",
    @field:SerializedName("lat") val lat: Double = 0.0,
    @field:SerializedName("lng") val lng: Double = 0.0
)

