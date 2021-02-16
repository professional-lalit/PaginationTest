package com.paginationtest.app.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clutchandgear.app.networking.NetworkError
import com.clutchandgear.app.networking.NetworkResult
import com.paginationtest.app.models.Book
import com.paginationtest.app.models.GoogleBook
import com.paginationtest.app.networking.response.BookListResponse
import com.paginationtest.app.repositories.BookRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class MainViewModel @ViewModelInject constructor(private val bookRepository: BookRepository) :
    ViewModel() {

    private val _errorData = MutableLiveData<NetworkError>()
    val errorData: LiveData<NetworkError> = _errorData

    private val _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>> = _bookList

    private var currentPage = AtomicInteger(0)

    fun getBooks() {
        viewModelScope.launch {
            val response = bookRepository.fetchBooks(currentPage.incrementAndGet())
            if (response is NetworkResult.Success) {
                val bookListResponse = response.model as BookListResponse
                if (bookListResponse.books?.isNotEmpty() == true)
                    _bookList.postValue(bookListResponse.books)
            } else if (response is NetworkResult.Error) {
                _errorData.postValue(response.networkError)
            }
        }
    }

}