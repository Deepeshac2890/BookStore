package com.example.bookstore.network

import com.example.bookstore.model.Book
import com.example.bookstore.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

/*
    Retrofit interface is created.
 */
@Singleton
interface BooksAPI {
    @GET(value = "volumes")
    suspend fun getAllBooks(
        @Query(value = "q") q: String, // Add req data type in place of String
    ) : Book

    @GET(value = "volumes/{bookId}")
    suspend fun getParticularBook(
        @Path(value = "bookId") bookId: String, // Add req data type in place of String
    ) : Item
}