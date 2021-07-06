package com.szareckii.map.viewmodel

interface Interactor<T> {

    suspend fun getData(): T
    suspend fun saveData(index: Int, name : String, latitude : Double, longitude : Double)
    suspend fun editData(index: Int, name: String, description: String)
}