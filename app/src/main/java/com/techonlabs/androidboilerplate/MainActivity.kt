package com.techonlabs.androidboilerplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.techonlabs.androidboilerplate.utils.extensions.submitListV2
import com.techonlabs.androidboilerplate.utils.extensions.toast
import com.techonlabs.androidboilerplate.utils.recyclerView.OnRecyclerItemClickListener
import com.techonlabs.androidboilerplate.utils.recyclerView.PagedRecyclerAdapter
import com.techonlabs.androidboilerplate.utils.recyclerView.RecyclerAdapter
import com.techonlabs.androidboilerplate.utils.recyclerView.StableId
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnRecyclerItemClickListener {

    private val vm: MainVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Inserting data in local database
        vm.fillDb()
//        addRecyclerAdapter()
        addPagedRecyclerAdapter()


    }

    fun addRecyclerAdapter() {
        RecyclerAdapter(mutableMapOf(FoodEntity::class to R.layout.list_cell), this).let { adapter ->
            foodListView.adapter = adapter
            vm.foodDao.get().observe(this, Observer {
                adapter.swapItems(it)
            })
        }
    }

    fun addPagedRecyclerAdapter() {
        PagedRecyclerAdapter(mutableMapOf(FoodEntity::class to R.layout.list_cell), this).let {
            foodListView.adapter = it
            vm.foodList.observe(this, Observer(it::submitListV2))
        }
    }

    override fun onRecyclerItemClick(obj: StableId) {
        super.onRecyclerItemClick(obj)
        if (obj is FoodEntity)
            toast(obj.name)
    }
}
