package com.szareckii.map.view.marks

import androidx.lifecycle.LiveData
import com.szareckii.map.model.data.AppState
import com.szareckii.map.model.data.DataModel
import com.szareckii.map.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MarksViewModel(private val interactor: MarksInteractor) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    private val liveDataMarksForViewToObserve: LiveData<DataModel> = _mutableLiveDataMarks

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData() {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor() }
    }

    fun saveData(index: Int, name: String, latitude: Double, longitude: Double) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            interactor.saveData(index, name, latitude, longitude) }
    }

    fun editData(index: Int, name: String, description: String) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            interactor.editData(index, name, description)
            startInteractor()
        }
    }

    fun deleteData(data : DataModel) {
        cancelJob()
        viewModelCoroutineScope.launch {
            interactor.deleteData(data)
        }

        _mutableLiveDataMarks.value = data
    }

    private suspend fun startInteractor() {
        _mutableLiveData.postValue(interactor.getData())
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}