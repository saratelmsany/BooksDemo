package com.sara.booksdemo.allBooks.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Author(

    @SerializedName("name")
    val name: String,

    @SerializedName("birth_year")
    val birthDay: Long,

    @SerializedName("death_year")
    val deathYear: Long,
): Serializable
