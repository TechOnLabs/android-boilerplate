package com.techonlabs.androidboilerplate

import androidx.room.Room
import com.techonlabs.androidboilerplate.datalayer.local.LocalDatabase
import com.techonlabs.androidboilerplate.datalayer.network.ApiInterface
import com.techonlabs.androidboilerplate.ui.AnimeVM
import com.techonlabs.androidboilerplate.ui.FoodVM
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val koinModule: Module = module {

    viewModel { FoodVM(get(), get()) }
    viewModel { AnimeVM(get(), get()) }

    single { get<LocalDatabase>().foodDao() }
    single { get<LocalDatabase>().animeDao() }


    // Room Database instance
    single {
        Room.databaseBuilder(androidContext(), LocalDatabase::class.java, "food-database")
                .fallbackToDestructiveMigration()
                .build()
    }

    single { ApiInterface.create() }

}