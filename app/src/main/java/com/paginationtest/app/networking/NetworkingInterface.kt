package com.clutchandgear.app.networkingimport retrofit2.Responseimport retrofit2.http.*interface NetworkingInterface {    @GET("books/book-list")    suspend fun fetchBooks(@Query("cursor") cursor: String?): Response<Any?>}