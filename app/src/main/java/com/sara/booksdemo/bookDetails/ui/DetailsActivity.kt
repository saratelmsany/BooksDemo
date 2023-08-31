package com.sara.booksdemo.bookDetails.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sara.booksdemo.R
import com.sara.booksdemo.allBooks.pojo.Author
import com.sara.booksdemo.allBooks.pojo.BookItem
import com.sara.booksdemo.bookDetails.viewModel.RelatedBooksViewModel
import com.sara.booksdemo.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity()  {

    private lateinit var mBinding : ActivityDetailsBinding

    lateinit var book: BookItem

    private  val relatedBookViewModel : RelatedBooksViewModel by viewModels()
    private var relatedAdapter : RelatedAdapter? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.relatedRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        book = intent.getSerializableExtra("book") as BookItem

        setScreenViews()

        relatedBookViewModel.booksList.observe(this,
            {
                setGridViewAdapter(it)
            })
        relatedBookViewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        controlProgressBar()

        relatedBookViewModel.getRelatedBooks(book.authors[0].name)
    }

    private fun setScreenViews(){
        Glide.with(this).load(book.formats.image).diskCacheStrategy(
            DiskCacheStrategy.ALL).thumbnail(0.5f).into(mBinding.bookImv)

        mBinding.nameTv.setText(book.title)

        for(item:Author in book.authors){
            mBinding.authorTv.append(item.name+"\n")
        }

        for(item:String in book.subjects){
            mBinding.subjectTv.append(item+", ")
        }
    }

    private fun setGridViewAdapter(booksList:List<BookItem>) {
        // removing current book from the list
         val books = booksList.filter {
            it.id != book.id && it.title != book.title
        }
        relatedAdapter = RelatedAdapter(this@DetailsActivity,books)
        mBinding.relatedRv.adapter = relatedAdapter

    }

    private fun controlProgressBar(){
        relatedBookViewModel.loading.observe(this, Observer {
            if (it) {
                mBinding.progressBar.visibility = View.VISIBLE
            } else {
                mBinding.progressBar.visibility = View.GONE
            }
        })
    }
}