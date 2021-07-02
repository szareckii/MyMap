package com.szareckii.map.view.marks

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

    override suspend fun saveData(name: String, latitude: Double, longitude: Double) {
        return repositoryLocal.saveData("Name$name", "description", latitude, longitude)
    }

    override suspend fun editData(id: Int, name: String, description: String) {
        return repositoryLocal.editData(1, name, description)
    }
}