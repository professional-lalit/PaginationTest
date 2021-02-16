package com.paginationtest.app.networking.response

import com.paginationtest.app.models.GoogleBook

class VolumeResponse (
    val kind: String,
    val totalItems: String,
    val items: List<GoogleBook>
)