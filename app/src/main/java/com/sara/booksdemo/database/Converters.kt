package com.sara.booksdemo.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sara.booksdemo.pojo.BookCover

class Converters {

//    @TypeConverter
//    fun toBookCover(string:String): BookCover {
//        return BookCover(string)
//    }
//
//    @TypeConverter
//    fun fromBookCover(bookCover: BookCover): String {
//       return bookCover.toString()
//    }

    @TypeConverter
    fun toBookCover(formats:String):BookCover{
        val type=object : TypeToken<BookCover>(){}.type
        return Gson().fromJson(formats,type)
    }
    @TypeConverter
    fun fromBookCover(formats:BookCover):String{
        val type=object :TypeToken<BookCover>(){}.type
        return Gson().toJson(formats,type)
    }
}