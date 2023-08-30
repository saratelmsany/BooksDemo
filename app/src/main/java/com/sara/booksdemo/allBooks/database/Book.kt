package com.sara.booksdemo.allBooks.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sara.booksdemo.allBooks.pojo.Author
import com.sara.booksdemo.allBooks.pojo.BookCover
import com.sara.booksdemo.allBooks.pojo.BookItem

@Entity
data class Book(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "formats")val formats : BookCover,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "authors", defaultValue = "") val authors: List<Author>,
    @ColumnInfo(name = "subjects", defaultValue = "") val subjects: List<String>,
    @ColumnInfo(name = "languages", defaultValue = "") val languages: List<String>,
    @ColumnInfo(name = "downloadCount", defaultValue = "0") val downloadCount: Long

    )

fun List<Book>.asDomainModel(): List<BookItem> {
    return map {
        BookItem(
            id = it.id,
            title = it.title,
            formats = it.formats,
            image = it.image,
            authors = it.authors,
            subjects = it.subjects,
            languages = it.languages,
            downloadCount = it.downloadCount
            )
    }
}

