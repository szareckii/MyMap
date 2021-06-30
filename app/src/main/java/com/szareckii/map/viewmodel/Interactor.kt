package com.szareckii.map.viewmodel

interface Interactor<T> {

    suspend fun getData(): T
    suspend fun saveData(latitude : Double, longitude : Double)
}