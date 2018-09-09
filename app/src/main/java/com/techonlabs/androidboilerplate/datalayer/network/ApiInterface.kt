package com.techonlabs.androidboilerplate.datalayer.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.techonlabs.androidboilerplate.AnimeModel
import com.techonlabs.androidboilerplate.BuildConfig
import kotlinx.coroutines.experimental.Deferred
import okhttp3.HttpUrl
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import timber.log.Timber


interface ApiInterface {

    @GET("anime/1/characters_staff")
    fun getAnime(): Deferred<Response<AnimeModel>>

    companion object {
        private const val BASE_URL = "https://api.jikan.moe/v3/"
        fun create(): ApiInterface = create(HttpUrl.parse(BASE_URL)!!)
        private fun create(httpUrl: HttpUrl) =
                Retrofit.Builder()
                        .baseUrl(httpUrl)
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .client(provideOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ApiInterface::class.java)


        private fun provideOkHttpClient(): OkHttpClient {
            val b = OkHttpClient.Builder()
            val loggingInterceptor = provideLoggingInterceptor()
            loggingInterceptor?.let { b.addInterceptor(loggingInterceptor) }
            //TODO: add HeaderInterceptor if you want to add AuthToken in your requests
//            b.addInterceptor(HeaderInterceptor())
            return b.build()
        }

        //Add interceptor only in debug build
        private fun provideLoggingInterceptor(): HttpLoggingInterceptor? {
            if (!BuildConfig.DEBUG) {
                return null
            }
            val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Timber.d(it)
            })
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }
    }
}