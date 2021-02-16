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
): PagingSource<Int, Book>() {

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = bookRepository.fetchBooks(currentLoadingPageKey)
            val responseData = mutableListOf<Book>()

            if (response is NetworkResult.Success) {
                val bookListResponse = response.model as BookListResponse
                if (bookListResponse.books?.isNotEmpty() == true)
                    responseData.addAll(bookListResponse.books)
            }

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}