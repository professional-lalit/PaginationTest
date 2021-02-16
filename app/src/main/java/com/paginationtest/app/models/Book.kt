package com.paginationtest.app.models

data class Book(
    val title: String?,
    val isbn: String?,
    val pageCount: Int?,
    val publishedDate: String?,
    val thumbnailUrl: String?,
    val shortDescription: String?,
    val longDescription: String?,
    val status: String?,
    val authors: List<String>?,
    val categories: List<String>?
)
        
data class BookDate(
    val date: String?
)