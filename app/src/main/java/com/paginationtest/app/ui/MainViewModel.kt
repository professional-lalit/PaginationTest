package com.paginationtest.app.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.clutchandgear.app.networking.NetworkError
import com.clutchandgear.app.networking.NetworkResult
import com.paginationtest.app.models.Book
import com.paginationtest.app.models.GoogleBook
import com.paginationtest.app.networking.paging.BookDataSource
import com.paginationtest.app.networking.response.BookListResponse
import com.paginationtest.app.repositories.BookRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class MainViewModel @ViewModelInject constructor(private val bookDataSource: BookDataSource) :
    ViewModel() {

    private val _errorData = MutableLiveData<NetworkError>()
    val errorData: LiveData<NetworkError> = _errorData

    private val _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>> = _bookList

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 5)
    ) {
        bookDataSource
    }.flow
        .cachedIn(viewModelScope)

}