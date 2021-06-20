package com.szareckii.map.model.repository

import com.szareckii.map.model.data.DataModel

interface Repository<T> {

    suspend fun getData(): T?
    suspend fun saveData(place: DataModel) {}
}