package com.szareckii.map.di

import com.szareckii.map.model.data.DataModel
import com.szareckii.map.model.repository.DataSource
import com.szareckii.map.model.repository.Repository
import com.szareckii.map.model.repository.RepositoryImplementation
import com.szareckii.map.view.marks.MarksInteractor
import com.szareckii.map.view.marks.MarksViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {

    single<Repository<List<DataModel>>> {
        RepositoryImplementation(DataSource().markers) }
}

val marksScreen = module {
    factory { MarksInteractor(get()) }
    viewModel { MarksViewModel(get()) }
}