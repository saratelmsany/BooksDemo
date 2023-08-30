package com.sara.booksdemo.allBooks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sara.booksdemo.allBooks.database.BookDAO
import com.sara.booksdemo.allBooks.database.asDomainModel
import com.sara.booksdemo.allBooks.pojo.BookItem
import com.sara.booksdemo.allBooks.pojo.BooksList
import com.sara.booksdemo.allBooks.pojo.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor (
    private val bookDAO: BookDAO, private val apiInterface: ApiInterface
) {


      fun getBooksFromDatabase(): MutableLiveData<List<BookItem>> {
         val list: MutableLiveData<List<BookItem>> = Transformations.map(bookDAO.getAllBooks()) {
             it.asDomainModel()
         } as MutableLiveData<List<BookItem>>
         return list
     }



      suspend fun refreshDatabase(nextPage:String) {
        withContext(Dispatchers.IO) {
            val books = apiInterface.getBooksFromRemote(nextPage).body()?.results

            Log.v("books",books.toString())
            bookDAO.insertAll(books!!.asDatabaseModel())
        }
    }


      suspend fun getBooksFromRemote(nextPage: String): Response<BooksList> {
          val response:Response<BooksList>
          withContext(Dispatchers.IO) {
              response = apiInterface.getBooksFromRemote(nextPage)
          }
         return response
     }


 }