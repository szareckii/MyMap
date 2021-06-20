package com.szareckii.map.model.repository

import com.szareckii.map.model.data.DataModel

class RepositoryImplementation(private val dataSource: MutableList<DataModel>) :
    Repository<List<DataModel>> {

//    var markers = dataSource

    override suspend fun getData(): List<DataModel> {
        return dataSource
    }

    override suspend fun saveData(place: DataModel) {
//        markers.add(place)
        dataSource.add(place)
    }
}