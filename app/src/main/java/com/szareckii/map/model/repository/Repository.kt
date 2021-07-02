package com.szareckii.map.model.repository

interface Repository<T> {

    suspend fun getData(): T?
    suspend fun saveData(name: String, description : String, lat: Double, lng : Double)
    suspend fun editData(id: Int, name: String, description : String)
}