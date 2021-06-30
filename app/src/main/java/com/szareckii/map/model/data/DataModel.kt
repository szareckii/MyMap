package com.szareckii.map.model.data

import com.google.gson.annotations.SerializedName

class DataModel(
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("lat") val lat: Double,
    @field:SerializedName("lng") val lng: Double
)

