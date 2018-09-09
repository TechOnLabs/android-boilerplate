package com.techonlabs.androidboilerplate.datalayer.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.techonlabs.androidboilerplate.FoodEntity

@Dao
interface FoodDao : BaseDao<FoodEntity> {

    @Query("SELECT * FROM FoodEntity")
    fun get(): LiveData<List<FoodEntity>>

    @Query("SELECT * FROM FoodEntity")
    fun getPaged(): DataSource.Factory<Int, FoodEntity>
}