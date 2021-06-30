package com.szareckii.map.view.favorites

import androidx.lifecycle.LiveData
import com.szareckii.map.model.data.AppState
import com.szareckii.map.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MarksViewModel(private val interactor: MarksInteractor) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData() {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor() }
    }

    fun saveData(latitude: Double, longitude: Double) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            interactor.saveData(latitude, longitude) }
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