package com.szareckii.map.di

import com.szareckii.map.model.data.DataModel
import com.szareckii.map.model.repository.DataSource
import com.szareckii.map.model.repository.Repository
import com.szareckii.map.model.repository.RepositoryImplementation
import com.szareckii.map.view.favorites.FavoritesViewModel
import com.szareckii.map.view.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {

    single<Repository<List<DataModel>>> {
        RepositoryImplementation(DataSource().markers) }
//        RepositoryImplementation(DataSource().markers) }
}

val mainScreen = module {
    viewModel { MainViewModel() }
}

val favoritesScreen = module {
    viewModel { FavoritesViewModel() }
}