package com.techonlabs.androidboilerplate.datalayer.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.techonlabs.androidboilerplate.VoiceActorEntity

@Dao
interface AnimeDao : BaseDao<VoiceActorEntity> {

    @Query("SELECT * FROM VoiceActorEntity")
    fun get(): LiveData<List<VoiceActorEntity>>

    @Query("SELECT * FROM VoiceActorEntity")
    fun getPaged(): DataSource.Factory<Int, VoiceActorEntity>
}