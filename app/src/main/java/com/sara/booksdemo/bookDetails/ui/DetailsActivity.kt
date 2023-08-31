package com.sara.booksdemo.bookDetails.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sara.booksdemo.R
import com.sara.booksdemo.allBooks.pojo.Author
import com.sara.booksdemo.allBooks.pojo.BookItem
import com.sara.booksdemo.allBooks.ui.BooksAdapter
import com.sara.booksdemo.allBooks.viewModel.BookViewModel
import com.sara.booksdemo.bookDetails.viewModel.RelatedBooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity()  {

    lateinit var book: BookItem
    lateinit var coverIm: ImageView
    lateinit var titleTv: TextView
    lateinit var subjectTv : TextView
    lateinit var authorTv : TextView
    lateinit var recyclerView : RecyclerView

    private  val relatedBookViewModel : RelatedBooksViewModel by viewModels()
    private var relatedAdapter : RelatedAdapter? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        coverIm = findViewById(R.id.bookImv)
        titleTv = findViewById(R.id.nameTv)
        subjectTv = findViewById(R.id.subjectTv)
        authorTv = findViewById(R.id.authorTv)
        recyclerView = findViewById(R.id.relatedRv)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        book = intent.getSerializableExtra("book") as BookItem

        setScreenViews()

        relatedBookViewModel.booksList.observe(this,
            {
                setGridViewAdapter(it)
            })
        relatedBookViewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        relatedBookViewModel.getRelatedBooks(book.authors[0].name)
    }

    private fun setScreenViews(){
        Glide.with(this).load(book.formats.image).diskCacheStrategy(
            DiskCacheStrategy.ALL).thumbnail(0.5f).into(coverIm)

        titleTv.setText(book.title)

        for(item:Author in book.authors){
            authorTv.append(item.name+"\n")
        }

        for(item:String in book.subjects){
            subjectTv.append(item+", ")
        }
    }

    private fun setGridViewAdapter(booksList:List<BookItem>) {
        // removing current book from the list
         val books = booksList.filter {
            it.id != book.id && it.title != book.title
        }
        relatedAdapter = RelatedAdapter(this@DetailsActivity,books)
        recyclerView.adapter = relatedAdapter

    }
}