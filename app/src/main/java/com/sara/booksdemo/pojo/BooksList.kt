package com.sara.booksdemo.pojo

import com.google.gson.annotations.SerializedName
import com.sara.booksdemo.database.Book


data class BooksList(

    @SerializedName("count")
    val count : Long,

    @SerializedName("next")
    val next : String,

    @SerializedName("previous")
    val previous: String,

    @SerializedName("results")
    val results : List<BookItem>

    )

    fun List<BookItem>.asDatabaseModel(): List<Book> {
        return map {
            Book(
                id = it.id,
                title = it.title,
                formats = it.formats,
                image = it.image
            )
        }
    }






