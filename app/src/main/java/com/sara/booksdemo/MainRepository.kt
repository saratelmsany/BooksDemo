package com.sara.booksdemo

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
import com.sara.booksdemo.pojo.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository constructor(private val apiInterface: ApiInterface,private val database: AppDatabase) {


 //   suspend fun getAllBooks(nextPage:String) = apiInterface.getBooks(nextPage)

    val list: MutableLiveData<List<BookItem>> = Transformations.map(database.bookDao().getAllBooks()) {
        it.asDomainModel()
    } as MutableLiveData<List<BookItem>>

    var next: String  = "2"

    suspend fun refreshVideos(nextPage:String) {
        withContext(Dispatchers.IO) {
            val books = apiInterface.getBooks(nextPage).body()?.results
            if (books != null) {
                Log.v("image", books.get(0).formats.toString())
            }
            database.bookDao().insertAll(books!!.asDatabaseModel())
        }
    }

    suspend fun getNextPage(page: String): MutableLiveData<List<BookItem>> {
        var books :MutableLiveData<List<BookItem>> = list
        withContext(Dispatchers.IO) {
            val response = apiInterface.getBooks(page)
            if(response.isSuccessful) {
                books.postValue(books.value!!.plus(response.body()!!.results))
                next = response.body()?.next?.last().toString()
                Log.v("nextPage",next)

            }
        }
        return books
    }




}