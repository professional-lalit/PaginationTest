package com.paginationtest.app.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.clutchandgear.app.networking.NetworkError
import com.clutchandgear.app.networking.NetworkResult
import com.paginationtest.app.models.Book
import com.paginationtest.app.models.GoogleBook
import com.paginationtest.app.networking.paging.BookDataSource
import com.paginationtest.app.networking.paging.BookPagingAdapter
import com.paginationtest.app.networking.response.BookListResponse
import com.paginationtest.app.repositories.BookRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class MainViewModel @ViewModelInject constructor(private val bookDataSource: BookDataSource) :
    ViewModel() {

    val bookPagingAdapter = BookPagingAdapter(BookPagingAdapter.BookComparator)
    val flow = Pager(PagingConfig(pageSize = 5)) { bookDataSource }
        .flow.cachedIn(viewModelScope)

    fun getBooks() {
        viewModelScope.launch {
            flow.collectLatest { pagingData ->
                bookPagingAdapter.submitData(pagingData)
            }
        }
    }

}