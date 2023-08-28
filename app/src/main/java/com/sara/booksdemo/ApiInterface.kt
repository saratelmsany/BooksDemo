package com.sara.booksdemo

import androidx.lifecycle.MutableLiveData
import com.sara.booksdemo.pojo.BookItem
import com.sara.booksdemo.pojo.BooksList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.AccessController.getContext

interface ApiInterface {

    @GET("books")
    suspend fun getBooksFromRemote(@Query("page") nextPage:String): Response<BooksList>

    fun getBooksFromDatabase():MutableLiveData<List<BookItem>>

     @GET("books")
     suspend fun refreshDatabase(@Query("page") nextPage:String)

     // interface
     // fun getBooks()

     //restRepo: interface
     // implement getBooks() {
     // let url = ...
     // }

    //dbRepo: interface
    // implement getBooks() {
     // let dbConnection = ...
     //
     // }

//    @GET("books")
//    suspend fun getNextPage(@Query("page") nextPage:String): MutableLiveData<List<BookItem>>



//    companion object {
//        private var apiInterface: ApiInterface? = null
//        fun getInstance() : ApiInterface {
//            val baseUrl =  App.getContext().resources.getString(R.string.base_url)
//            if (apiInterface == null) {
//                val retrofit = Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                apiInterface = retrofit.create(ApiInterface::class.java)
//            }
//            return apiInterface!!
//        }
//
//    }

}