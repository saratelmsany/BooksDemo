package com.sara.booksdemo.allBooks.pojo


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BookCover (
    @SerializedName("image/jpeg")
    val image : String,

): Serializable