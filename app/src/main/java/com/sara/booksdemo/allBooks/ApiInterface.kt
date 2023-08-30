package com.sara.booksdemo.allBooks

    import com.sara.booksdemo.allBooks.pojo.BooksList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("books")
    suspend fun getBooksFromRemote(@Query("page") nextPage:String): Response<BooksList>



}