package com.szareckii.map.view.favorites

import com.szareckii.map.model.data.AppState
import com.szareckii.map.model.data.DataModel
import com.szareckii.map.model.repository.Repository
import com.szareckii.map.viewmodel.Interactor

class MarksInteractor(
    private val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(): AppState {
        return AppState.Success(repositoryLocal.getData())
    }

    override suspend fun saveData(lat: Double, lng: Double) {
        return repositoryLocal.saveData("name", "description", lat, lng)
    }
}