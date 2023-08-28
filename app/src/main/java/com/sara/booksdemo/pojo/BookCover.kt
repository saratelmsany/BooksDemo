package com.sara.booksdemo.pojo

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class BookCover (
    @SerializedName("image/jpeg")
    val image : String,

)
