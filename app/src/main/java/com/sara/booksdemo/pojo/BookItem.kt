package com.sara.booksdemo.pojo

import com.google.gson.annotations.SerializedName
import dagger.Provides
import javax.inject.Inject


data class BookItem (

    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
   val title: String,

    @SerializedName("formats")
    val formats: BookCover,

    @SerializedName("image/jpeg")
     val image: String? = formats.image

    )


