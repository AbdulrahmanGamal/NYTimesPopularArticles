package com.abdulrahman.nytimes.main.ui.main.di

import com.abdulrahman.nytimes.main.ui.main.data.PopularArticlesRepository
import com.abdulrahman.nytimes.main.ui.main.viewModel.PopularArticlesViewModel
import com.abdulrahman.nytimes.main.utilities.NetworkConnection
import org.koin.dsl.module

val mainModule = module {
    factory { PopularArticlesViewModel(get(), get()) }
    single { NetworkConnection(get()) }

    single {
        PopularArticlesRepository(get())
    }
}