package com.techonlabs.androidboilerplate.utils

enum class RequestState(var message: String = "") {
    Fetching, Success, Failed, NetworkFail
}