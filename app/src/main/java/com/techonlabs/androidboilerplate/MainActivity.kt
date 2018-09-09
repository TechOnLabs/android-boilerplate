package com.techonlabs.androidboilerplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val vm: MainVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Inserting data in local database
        vm.fillDb()
        vm.foodDao.get().observe(this, Observer {
            Timber.tag("Foood").i(it.toString()+"\n")
        })
    }
}
