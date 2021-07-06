package com.szareckii.map.model.repository

import com.szareckii.map.model.data.DataModel

class RepositoryImplementation(private val dataSource: MutableList<DataModel>) :
    Repository<List<DataModel>> {

//    var markers = dataSource

    override suspend fun getData(): List<DataModel> {
        return dataSource
    }

    override suspend fun saveData(
        index: Int, name: String, description: String, lat: Double, lng: Double) {

        val place = DataModel(dataSource.size, name, description, lat, lng)
        dataSource.add(place)
    }

    override suspend fun editData(index: Int, name: String, description: String) {
        dataSource[index].name = name
        dataSource[index].description = description
    }

    override suspend fun getSizeData(): Int {
        return dataSource.size
    }
}