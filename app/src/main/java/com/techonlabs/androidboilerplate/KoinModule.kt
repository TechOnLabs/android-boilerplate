package com.techonlabs.androidboilerplate

import androidx.room.Room
import com.techonlabs.androidboilerplate.datalayer.local.LocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val myModule: Module = module {

    viewModel { MainVM(get()) }

    single { get<LocalDatabase>().foodDao() }


    // Room Database instance
    single {
        Room.databaseBuilder(androidContext(), LocalDatabase::class.java, "food-database")
                .fallbackToDestructiveMigration()
                .build()
    }

}