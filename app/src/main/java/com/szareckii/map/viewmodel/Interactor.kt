package com.szareckii.map.viewmodel

interface Interactor<T> {

    suspend fun getData(): T
    suspend fun saveData(name : String, latitude : Double, longitude : Double)
}