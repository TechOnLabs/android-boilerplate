package com.techonlabs.androidboilerplate.datalayer.network

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException


object Network {

    fun <T> request(callFunc: (() -> Deferred<Response<T>>), callbackFunc: (() -> NetworkCallback<T>), parentJob: Job) {
        val callback = callbackFunc()
        request(callFunc = callFunc,
                onSuccess = callback.success,
                onHttpError = callback.httpError,
                onErrors = callback.error,
                callbackFunc = callbackFunc,
                parentJob = parentJob)
    }

    private fun <T> request(callFunc: (() -> Deferred<Response<T>>),
                            onSuccess: ((T) -> Unit)?,
                            onHttpError: ((Response<T>) -> Unit)?,
                            onErrors: ((Throwable) -> Unit)?,
                            callbackFunc: (() -> NetworkCallback<T>),
                            parentJob: Job) {
        var fetchState: FetchState
        launch(UI, parent = parentJob) {
            try {
                onSuccess?.let {
                    val response = callFunc().await()
                    if (response.isSuccessful) {
                        fetchState = FetchState.SUCCESS
                        onSuccess(response.body()!!)
                    } else {
                        fetchState = FetchState.FAILED
                        //Basically http exceptions like 404 or 500
                        // a non-2XX response was received
                        httpErrorHandler(response = response, callFunc = callFunc, callbackFunc = callbackFunc, parentJob = parentJob)
                        onHttpError?.let {
                            onHttpError(response)
                        }
                    }
                }
            } catch (t: Throwable) {
                fetchState = FetchState.FAILED
                // a networking or data conversion error
                onErrors?.let {
                    if (t is IOException) {
                        //network error
                        Timber.e(t)
                        errorHandler(callFunc = callFunc, callbackFunc = callbackFunc, parentJob = parentJob)
                        onErrors(t)
                    }
                }
            }
        }
    }


    private fun <T> httpErrorHandler(response: Response<T>, callFunc: (() -> Deferred<Response<T>>), callbackFunc: (() -> NetworkCallback<T>), parentJob: Job) {
        //TODO:Add code fo error handling
        when (response.code()) {
            HttpErrors.NOT_FOUND.value -> {

            }
        }
    }

    private fun <T> errorHandler(callFunc: (() -> Deferred<Response<T>>), callbackFunc: (() -> NetworkCallback<T>), parentJob: Job) {
        //TODO:Add code fo error handling
    }
}

enum class HttpErrors(val value: Int) {
    NOT_FOUND(404)
}

enum class FetchState {
    FETCHING,
    SUCCESS,
    FAILED
}

