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
//
//data class AnimeModel(
//        val requestHash: String,
//        val requestCached: Boolean,
//        val requestCacheExpiry: Int,
//        val characters: List<CharacterEntity>
//)
//
//
//data class CharacterEntity(
//        val malId: Int,
//        val url: String,
//        val imageUrl: String,
//        val name: String,
//        val role: String,
//        val voiceActors: List<VoiceActorEntity>
//)
//
//@Entity
//data class VoiceActorEntity(
//        @PrimaryKey val malId: Int,
//        val name: String,
//        val url: String,
//        val imageUrl: String,
//        val language: String
//) : StableId {
//    override fun getStableId() = malId.toString()
//}

data class AnimeModel(
        val request_hash: String,
        val request_cached: Boolean,
        val request_cache_expiry: Int,
        val characters: List<Character>,
        val staff: List<Staff>
)

data class Staff(
        val mal_id: Int,
        val url: String,
        val name: String,
        val image_url: String,
        val positions: List<String>
)

data class Character(
        val mal_id: Int,
        val url: String,
        val image_url: String,
        val name: String,
        val role: String,
        val voice_actors: List<VoiceActorEntity>
)

@Entity
data class VoiceActorEntity(
        @PrimaryKey val mal_id: Int,
        val name: String,
        val url: String,
        val image_url: String,
        val language: String
) : StableId {
    override fun getStableId() = mal_id.toString()
}