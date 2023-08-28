package com.sara.booksdemo

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import com.sara.booksdemo.database.AppDatabase
import com.sara.booksdemo.database.Book
import com.sara.booksdemo.database.BookDAO
import com.sara.booksdemo.database.asDomainModel
import com.sara.booksdemo.pojo.BookItem
import com.sara.booksdemo.pojo.BooksList
import com.sara.booksdemo.pojo.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor (
    private val bookDAO: BookDAO, private val apiInterface: ApiInterface) {


      fun getBooksFromDatabase(): MutableLiveData<List<BookItem>> {
         val list: MutableLiveData<List<BookItem>> = Transformations.map(bookDAO.getAllBooks()) {
             it.asDomainModel()
         } as MutableLiveData<List<BookItem>>
         return list
     }

//     val list: MutableLiveData<List<BookItem>> = Transformations.map(bookDAO.getAllBooks()) {
//        it.asDomainModel()
//    } as MutableLiveData<List<BookItem>>




      suspend fun refreshDatabase(nextPage:String) {
        withContext(Dispatchers.IO) {
            val books = apiInterface.getBooksFromRemote(nextPage).body()?.results

            Log.v("books",books.toString())
            bookDAO.insertAll(books!!.asDatabaseModel())
        }
    }

   //  var next: String  = "2"
//     override suspend fun getNextPage(page: String): MutableLiveData<List<BookItem>> {
//        var books :MutableLiveData<List<BookItem>> = list
//        withContext(Dispatchers.IO) {
//            val response = getBooksFromRemote(page)
//            if(response.isSuccessful) {
//                books.postValue(books.value!!.plus(response.body()!!.results))
//                var next = response.body()?.next?.last().toString()
//                Log.v("nextPage",next)
//
//            }
//        }
//        return books
//    }



      suspend fun getBooksFromRemote(nextPage: String): Response<BooksList> {
          val response:Response<BooksList>
          withContext(Dispatchers.IO) {
              response = apiInterface.getBooksFromRemote(nextPage)
          }
         return response
     }


 }