package com.sara.booksdemo.allBooks.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sara.booksdemo.databinding.ActivityMainBinding
import com.sara.booksdemo.allBooks.pojo.BookItem
import com.sara.booksdemo.allBooks.viewModel.BookViewModel
import com.sara.booksdemo.bookDetails.ui.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    private  val bookViewModel : BookViewModel by viewModels()
    private var booksAdapter : BooksAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

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
            mBinding.booksGrid.adapter = booksAdapter
            mBinding.booksGrid.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    startActivity(intent)
                }
        } else {
            booksAdapter!!.changeModelList(booksList)
          //  booksGridView.smoothScrollByOffset(1)
        }
    }

    private  fun pagination(){
        mBinding.booksGrid.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {}
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    val lastVisibleItem = mBinding.booksGrid.lastVisiblePosition
                    val totalItemCount = mBinding.booksGrid.count

                    if (lastVisibleItem + 1 == totalItemCount) {

                        controlProgressBar()

                        bookViewModel.getNextPage()

                    }

                }
            }
        })
    }



}