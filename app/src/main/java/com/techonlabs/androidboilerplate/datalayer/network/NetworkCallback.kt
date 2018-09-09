package com.techonlabs.androidboilerplate.datalayer.network

import retrofit2.Response

class NetworkCallback<T> {
    var success: ((T) -> Unit) ?= null
    var httpError: ((Response<T>)-> Unit) ?= null
    var error: ((Throwable)-> Unit) ?= null
}