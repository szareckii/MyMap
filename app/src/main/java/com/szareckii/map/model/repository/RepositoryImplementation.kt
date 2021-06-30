package com.szareckii.map.model.repository

import com.szareckii.map.model.data.DataModel

class RepositoryImplementation(private val dataSource: MutableList<DataModel>) :
    Repository<List<DataModel>> {

//    var markers = dataSource

    override suspend fun getData(): List<DataModel> {
        return dataSource
    }

    override suspend fun saveData(name: String, description: String, lat: Double, lng: Double) {
//        markers.add(place)

        val place = DataModel(name, description, lat, lng)
        dataSource.add(place)
    }
}