package com.sara.booksdemo.allBooks.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDAO {

    @Query("SELECT * FROM book")
    fun getAllBooks(): LiveData<List<Book>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(books: List<Book>)


}

