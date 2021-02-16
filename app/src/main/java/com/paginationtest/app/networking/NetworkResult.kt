package com.clutchandgear.app.networking

import java.lang.Exception

sealed class NetworkResult {
    class Success(val model: Any?) : NetworkResult()

    class Error(val networkError: NetworkError) : NetworkResult()
}

data class NetworkError(
    val message: String
)