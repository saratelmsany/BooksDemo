package com.sara.booksdemo.ui

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
import androidx.lifecycle.ViewModelProvider
import com.sara.booksdemo.ApiInterface
import com.sara.booksdemo.MainRepository
import com.sara.booksdemo.MyViewModelFactory
import com.sara.booksdemo.R
import com.sara.booksdemo.databinding.ActivityMainBinding
import com.sara.booksdemo.pojo.BookItem
import com.sara.booksdemo.viewModel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit  var booksGridView : GridView
    private lateinit var mBinding : ActivityMainBinding
    private  val bookViewModel : BookViewModel by viewModels()
    private var booksAdapter : BooksAdapter? = null

  //  lateinit var booksAdapter : BooksAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //   setContentView(R.layout.activity_main)
        booksGridView = findViewById(R.id.books_grid)

//        val apiInterface = ApiInterface.getInstance()
//        val mainRepository = MainRepository(apiInterface, getDatabase(this))

//        bookViewModel =
//            ViewModelProvider(this@MainActivity, MyViewModelFactory(mainRepository)).
//            get(BookViewModel::class.java)

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
                    Toast.makeText(
                        applicationContext, booksList[+position].title,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Log.v("data changed", booksList.size.toString())
            booksAdapter!!.changeModelList(booksList)
            booksGridView.smoothScrollByOffset(1)
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




//    private suspend fun retrofitClient() {
//      //  GlobalScope.launch(Dispatchers.IO) {
//            val baseUrl = getString(R.string.base_url)
//            val api =
//                Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                    .create(ApiInterface::class.java)
//
//        GlobalScope.launch(Dispatchers.IO){
//            val response = api.getBooks()
//            if (response.isSuccessful){
//                Log.v("CHECK-RESPONSE", "on response " + response.body().toString())
//                val responseList = response.body()?.results?.toMutableList()
//                        booksList = responseList!!
//                        withContext(Dispatchers.Main) {
//                               setGridViewAdapter()
//                        }
//
//            }else{
//                    Log.v("CHECK-RESPONSE", "on failure " + response.message().toString())
//
//            }
//
//        }
////            api.getBooks().enqueue(object : Callback<BooksList> {
////                override fun onResponse(call: Call<BooksList>, response: Response<BooksList>) {
////                    if (response.isSuccessful) {
////                        Log.v(
////                            "CHECK-RESPONSE",
////                            "on response " + response.body()?.results.toString()
////                        )
////                        val responseList = response.body()?.results?.toMutableList()
////                        booksList = responseList!!
////                      //  withContext(Dispatchers.Main) {
////                               setGridViewAdapter()
////                      //  }
////                    }
////                }
////                override fun onFailure(call: Call<BooksList>, t: Throwable) {
////                    Log.v("CHECK-RESPONSE", "on failure " + t.message.toString())
////                }
////            })
//
//     //   }
//
//    }



}