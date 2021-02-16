package com.paginationtest.app.repositories

import androidx.lifecycle.MutableLiveData
import com.clutchandgear.app.networking.NetworkError
import com.clutchandgear.app.networking.NetworkResult
import com.clutchandgear.app.networking.NetworkingInterface
import com.google.gson.Gson
import com.paginationtest.app.Constants
import com.paginationtest.app.models.GoogleBook
import com.paginationtest.app.networking.response.BookListResponse
import com.paginationtest.app.networking.response.VolumeResponse
import okhttp3.ResponseBody
import javax.inject.Inject

class BookRepository @Inject constructor(private val networkingInterface: NetworkingInterface) {


    suspend fun fetchBooks(page: Int): NetworkResult {
        val response = networkingInterface.fetchBooks(page = page)
        return if (response.isSuccessful && response.code() in 200..210) {
            val bookList = Gson().fromJson(
                Gson().toJson(response.body()), BookListResponse::class.java
            )
            NetworkResult.Success(bookList)
        } else {
            val netError = getNetworkError(response.errorBody())
            NetworkResult.Error(netError)
        }
    }

    private fun getNetworkError(errorBody: ResponseBody?): NetworkError {
        return NetworkError(errorBody?.string() ?: "")
    }

}