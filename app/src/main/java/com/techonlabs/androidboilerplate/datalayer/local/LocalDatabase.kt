package com.techonlabs.androidboilerplate.datalayer.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techonlabs.androidboilerplate.FoodEntity

/**Always increase db version while making changes in db and also add the Entity class as well*/
@Database(entities = [FoodEntity::class],
        version = 1,
        exportSchema = false)
@TypeConverters(value = [DBTypeConverter::class])
abstract class LocalDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}