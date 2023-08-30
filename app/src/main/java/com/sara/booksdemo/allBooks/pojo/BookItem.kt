package com.sara.booksdemo.allBooks.pojo

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class BookItem (

    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
   val title: String,

    @SerializedName("formats")
    val formats: BookCover,

    @SerializedName("image/jpeg")
     val image: String? = formats.image,

    @SerializedName("authors")
    val authors : List<Author>,

    @SerializedName("subjects")
     val subjects : List<String>,

    @SerializedName("languages")
     val languages : List<String>,

    @SerializedName("download_count")
    val downloadCount : Long,

    ):Serializable