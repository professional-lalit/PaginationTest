package com.paginationtest.app.models

data class GoogleBook(
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: Int,
    val industryIdentifiers: List<IndustryIdentifiers>
)

data class IndustryIdentifiers(
    val type: String,
    val identifier: String
)