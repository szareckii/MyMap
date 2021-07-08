package com.szareckii.map.model.repository

import com.szareckii.map.model.data.DataModel

interface Repository<T> {

    suspend fun getData(): T?
    suspend fun getSizeData(): Int
    suspend fun saveData(index: Int, name: String, description : String, lat: Double, lng : Double)
    suspend fun editData(index: Int, name: String, description : String)
    suspend fun deleteData(data: DataModel)

}