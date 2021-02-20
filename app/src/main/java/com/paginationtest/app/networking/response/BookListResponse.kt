package com.paginationtest.app.networking.response

import com.paginationtest.app.models.Book

data class BookListResponse(
    val books: List<Book>?,
    val totalPages: Int?,
    val next: String?
)