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

    override suspend fun saveData(index: Int, name: String, latitude: Double, longitude: Double) {
        return repositoryLocal.saveData(index,"Name$name", "description", latitude, longitude)
    }

    override suspend fun editData(index: Int, name: String, description: String) {
        return repositoryLocal.editData(index, name, description)
    }

    override suspend fun deleteData(data: DataModel) {
        return repositoryLocal.deleteData(data)
    }
}