package com.sara.booksdemo.bookDetails

import com.sara.booksdemo.allBooks.pojo.BooksList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RelatedBooksApiInterface {

    @GET("books")
    suspend fun getBooksWithAuthor(@Query("search") author:String): Response<BooksList>

}