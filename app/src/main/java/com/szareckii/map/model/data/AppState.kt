package com.szareckii.map.model.data

sealed class AppState {
    data class Success(val data: MutableList<DataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}