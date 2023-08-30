package com.sara.booksdemo.bookDetails

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sara.booksdemo.R
import com.sara.booksdemo.allBooks.pojo.Author
import com.sara.booksdemo.allBooks.pojo.BookItem


class DetailsActivity : AppCompatActivity() {

    lateinit var book: BookItem
    lateinit var coverIm: ImageView
    lateinit var titleTv: TextView
    lateinit var subjectTv : TextView
    lateinit var authorTv : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

   //     val displayMetrics = DisplayMetrics()
   //     windowManager.defaultDisplay.getMetrics(displayMetrics)
//        val height = displayMetrics.heightPixels
//        val width = displayMetrics.widthPixels

        coverIm = findViewById(R.id.bookImv)
        titleTv = findViewById(R.id.nameTv)
        subjectTv = findViewById(R.id.subjectTv)
        authorTv = findViewById(R.id.authorTv)

//        coverIm.layoutParams.width = width/3,
//        coverIm.layoutParams.height = height/3
//        titleTv.layoutParams.width = width/3
//        titleTv.layoutParams.height = height/3

        book = intent.getSerializableExtra("book") as BookItem

        Glide.with(this).load(book.formats.image).diskCacheStrategy(
            DiskCacheStrategy.ALL).thumbnail(0.5f).into(coverIm)

        titleTv.setText(book.title)

        for(item:String in book.subjects){
            subjectTv.append(item+", ")
        }

        for(item:Author in book.authors){
            authorTv.append(item.name+"\n")
        }

    }
}