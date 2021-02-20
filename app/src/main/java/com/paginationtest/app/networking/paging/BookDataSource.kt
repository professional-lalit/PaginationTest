package com.paginationtest.app.networking.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.clutchandgear.app.networking.NetworkResult
import com.clutchandgear.app.networking.NetworkingInterface
import com.paginationtest.app.models.Book
import com.paginationtest.app.networking.response.BookListResponse
import com.paginationtest.app.repositories.BookRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class BookDataSource @Inject constructor(
    private val bookRepository: BookRepository
) : PagingSource<String, Book>() {


    override suspend fun load(params: LoadParams<String>): LoadResult<String, Book> {
        try {
            val currentLoadingPageCursor = params.key ?: ""
            val response = bookRepository.fetchBooks(currentLoadingPageCursor)
            val responseData = mutableListOf<Book>()
            var nextCursor: String? = ""

            if (response is NetworkResult.Success) {
                val bookListResponse = response.model as BookListResponse
                nextCursor = bookListResponse.next
                if (bookListResponse.books?.isNotEmpty() == true)
                    responseData.addAll(bookListResponse.books)
            }

            val prevCursor = ""

            return LoadResult.Page(
                data = responseData,
                prevKey = nextCursor,
                nextKey = nextCursor
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Book>): String? {
        return state.pages.last().nextKey
    }
}