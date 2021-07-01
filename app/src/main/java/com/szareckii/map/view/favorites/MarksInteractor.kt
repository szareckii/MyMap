package com.szareckii.map.view.favorites

import com.szareckii.map.model.data.AppState
import com.szareckii.map.model.data.DataModel
import com.szareckii.map.model.repository.Repository
import com.szareckii.map.viewmodel.Interactor

class MarksInteractor(
    private val repositoryLocal: Repository<MutableList<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(): AppState {
        return AppState.Success(repositoryLocal.getData())
    }

    override suspend fun saveData(name : String, lat: Double, lng: Double) {
        return repositoryLocal.saveData("Name$name", "description", lat, lng)
    }
}