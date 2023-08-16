package com.sara.booksdemo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sara.booksdemo.pojo.BookCover
import com.sara.booksdemo.pojo.BookItem

@Entity
data class Book(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "formats")val formats : BookCover,
    @ColumnInfo(name = "image") val image: String?
)

fun List<Book>.asDomainModel(): List<BookItem> {
    return map {
        BookItem(
            id = it.id,
            title = it.title,
            formats = it.formats,
            )
    }
}

