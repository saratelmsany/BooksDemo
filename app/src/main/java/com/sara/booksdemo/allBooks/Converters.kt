package com.sara.booksdemo.allBooks

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sara.booksdemo.allBooks.pojo.Author
import com.sara.booksdemo.allBooks.pojo.BookCover

class Converters {

    @TypeConverter
    fun toBookCover(formats:String): BookCover {
        val type=object : TypeToken<BookCover>(){}.type
        return Gson().fromJson(formats,type)
    }
    @TypeConverter
    fun fromBookCover(formats: BookCover):String{
        val type=object :TypeToken<BookCover>(){}.type
        return Gson().toJson(formats,type)
    }

    @TypeConverter
    fun convertAuthorsListToJSONString(authors: List<Author>): String {
        return Gson().toJson(authors)
    }
    @TypeConverter
    fun convertJSONStringToAuthorList(jsonString: String): List<Author> {
        val type=object :TypeToken<List<Author>>(){}.type
        return Gson().fromJson(jsonString, type)
    }


    @TypeConverter
    fun convertSubjectsListToJSONString(subjects: List<String>): String
            = Gson().toJson(subjects)
    @TypeConverter
    fun convertJSONStringToSubjectsList(jsonString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

}