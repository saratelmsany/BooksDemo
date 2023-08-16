package com.sara.booksdemo.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.sara.booksdemo.pojo.BookItem

@Dao
interface BookDAO {

    @Query("SELECT * FROM book")
    fun getAllBooks(): LiveData<List<Book>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(books: List<Book>)

    @Delete
    fun delete(book: Book)

}

