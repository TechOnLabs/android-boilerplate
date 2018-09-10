package com.techonlabs.androidboilerplate.datalayer.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.techonlabs.androidboilerplate.utils.RequestState
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException


object Network {

    fun <T> request(networkCall: Deferred<Response<T>>, callback: NetworkCallback<T>, parentJob: Job,
                    requestState: MutableLiveData<RequestState?>) {
        request(networkCall = networkCall,
                onSuccess = callback.success,
                onHttpError = callback.httpError,
                onErrors = callback.error,
                callback = callback,
                parentJob = parentJob,
                requestState = requestState)
    }

    private fun <T> request(networkCall: Deferred<Response<T>>,
                            onSuccess: ((T) -> Unit)?,
                            onHttpError: ((Response<T>) -> Unit)?,
                            onErrors: ((Throwable) -> Unit)?,
                            callback: NetworkCallback<T>,
                            parentJob: Job, requestState: MutableLiveData<RequestState?>) {
        requestState.value = RequestState.Fetching
        requestState.value
        launch(UI, parent = parentJob) {
            try {
                val response = networkCall.await()
                if (response.isSuccessful) {
                    RequestState.Success.let {
                        it.message = response.message()
                        requestState.value = it
                    }

                    onSuccess?.let {
                        onSuccess(response.body()!!)
                    }
                } else {
                    RequestState.Failed.let {
                        it.message = response.message()
                        requestState.value = it
                    }
                    //Basically http exceptions like 404 or 500
                    // a non-2XX response was received
                    httpErrorHandler(response = response, networkCall = networkCall, callback = callback, parentJob = parentJob)
                    onHttpError?.let {
                        onHttpError(response)
                    }
                }

            } catch (t: Throwable) {
                RequestState.NetworkFail.let {
                    it.message = t.message?:"Error occurred"
                    requestState.value = it
                }
                // a networking or data conversion error
                onErrors?.let {
                    if (t is IOException) {
                        //network error
                        Timber.e(t)
                        errorHandler(networkCall = networkCall, callback = callback, parentJob = parentJob)
                        onErrors(t)
                    }
                }
            }
        }
    }


    private fun <T> httpErrorHandler(response: Response<T>, networkCall: Deferred<Response<T>>, callback: NetworkCallback<T>, parentJob: Job) {
        //TODO:Add code fo error handling
        when (response.code()) {
            HttpErrors.NOT_FOUND.value -> {

            }
        }
    }

    private fun <T> errorHandler(networkCall: Deferred<Response<T>>, callback: NetworkCallback<T>, parentJob: Job) {
        //TODO:Add code fo error handling
    }
}

enum class HttpErrors(val value: Int) {
    NOT_FOUND(404)
}

