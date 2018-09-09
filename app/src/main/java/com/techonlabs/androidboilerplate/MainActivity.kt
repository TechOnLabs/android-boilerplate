package com.techonlabs.androidboilerplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.techonlabs.androidboilerplate.utils.extensions.submitListV2
import com.techonlabs.androidboilerplate.utils.recyclerView.PagedRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm: MainVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Inserting data in local database
        vm.fillDb()

        PagedRecyclerAdapter(mutableMapOf(FoodEntity::class to R.layout.list_cell)).let {
            foodListView.adapter = it
            vm.foodList.observe(this, Observer(it::submitListV2))
        }

    }
}
