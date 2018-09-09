package com.techonlabs.androidboilerplate.datalayer.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authToken = "" //TODO: add your authtoken here
        Timber.i(authToken)
        if (authToken == null || authToken.isBlank())
            return chain.proceed(request)
        val newRequest = request.newBuilder().addHeader("Authorization", "Bearer $authToken")
        return chain.proceed(newRequest.build())
    }
}