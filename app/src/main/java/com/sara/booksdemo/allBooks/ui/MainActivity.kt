package com.sara.booksdemo.allBooks.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sara.booksdemo.R
import com.sara.booksdemo.databinding.ActivityMainBinding
import com.sara.booksdemo.allBooks.pojo.BookItem
import com.sara.booksdemo.allBooks.ui.BooksAdapter
import com.sara.booksdemo.allBooks.viewModel.BookViewModel
import com.sara.booksdemo.bookDetails.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit  var booksGridView : GridView
    private lateinit var mBinding : ActivityMainBinding
    private  val bookViewModel : BookViewModel by viewModels()
    private var booksAdapter : BooksAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        booksGridView = findViewById(R.id.books_grid)

        bookViewModel.booksList.observe(this,
            {
                setGridViewAdapter(it)
                pagination()
            })
        bookViewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        controlProgressBar()

        bookViewModel.refreshDataFromRepository()

    }


    private fun controlProgressBar(){
        bookViewModel.loading.observe(this, Observer {
            if (it) {
                mBinding.progressBar.visibility = View.VISIBLE
            } else {
                mBinding.progressBar.visibility = View.GONE
            }
        })
    }


    private fun setGridViewAdapter(booksList:List<BookItem>) {
        if (booksAdapter == null) {
            booksAdapter = BooksAdapter(this@MainActivity, booksList)
            booksGridView.adapter = booksAdapter
            booksGridView.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->

                    Log.v("bookItem",booksList[position].toString())
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    intent.putExtra("book", booksList[position])
                    startActivity(intent)
                }
        } else {
            Log.v("data changed", booksList.size.toString())
            booksAdapter!!.changeModelList(booksList)
          //  booksGridView.smoothScrollByOffset(1)
        }
    }

    private  fun pagination(){
        booksGridView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {}
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    val lastVisibleItem = booksGridView.lastVisiblePosition
                    val totalItemCount = booksGridView.count

                    if (lastVisibleItem + 1 == totalItemCount) {
                        Log.v("totalItemCount",totalItemCount.toString())

                        controlProgressBar()

                        bookViewModel.getNextPage()

                    }

                }
            }
        })
    }



}