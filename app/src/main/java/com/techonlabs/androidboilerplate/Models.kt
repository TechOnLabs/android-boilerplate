package com.techonlabs.androidboilerplate

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techonlabs.androidboilerplate.utils.recyclerView.StableId

@Entity
data class FoodEntity(
        @PrimaryKey val id: Int,
        val name: String
) : StableId {
    override fun getStableId() = id.toString()
}