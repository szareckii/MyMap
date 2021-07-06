package com.szareckii.map.model.repository

interface Repository<T> {

    suspend fun getData(): T?
    suspend fun getSizeData(): Int
    suspend fun saveData(index: Int, name: String, description : String, lat: Double, lng : Double)
    suspend fun editData(index: Int, name: String, description : String)
}