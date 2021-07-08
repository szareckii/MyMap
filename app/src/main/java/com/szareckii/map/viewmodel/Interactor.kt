package com.szareckii.map.viewmodel

import com.szareckii.map.model.data.DataModel

interface Interactor<T> {

    suspend fun getData(): T
    suspend fun saveData(index: Int, name : String, latitude : Double, longitude : Double)
    suspend fun editData(index: Int, name: String, description: String)
    suspend fun deleteData(data: DataModel)
}