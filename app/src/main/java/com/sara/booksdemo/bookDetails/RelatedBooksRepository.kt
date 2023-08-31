package com.sara.booksdemo.bookDetails

import android.util.Log
import com.sara.booksdemo.allBooks.pojo.BooksList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RelatedBooksRepository @Inject constructor(
    private val apiInterface: RelatedBooksApiInterface) {

    suspend fun getRelatedBooks(author: String): Response<BooksList> {
        val response: Response<BooksList>
        withContext(Dispatchers.IO) {
            response = apiInterface.getBooksWithAuthor(author)
            Log.v("relatedResponse",response.body().toString())
        }
        return response
    }

}