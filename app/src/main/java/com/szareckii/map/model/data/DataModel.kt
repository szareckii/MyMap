package com.szareckii.map.model.data

import com.google.gson.annotations.SerializedName

class DataModel(
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("lan") val lan: Double,
    @field:SerializedName("lon") val lon: Double,
    @field:SerializedName("favorite") val favorite: Boolean?
)
