package com.sara.booksdemo.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sara.booksdemo.ApiInterface
import com.sara.booksdemo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor
    (private val bookRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
  //  var booksList = MutableLiveData<List<BookItem>>()
    private var job: Job? = null
    private lateinit var nextPage: String
    val booksList  = bookRepository.getBooksFromDatabase()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()
    private var _eventNetworkError = MutableLiveData(false)


    fun refreshDataFromRepository() {
        loading.value = true
        nextPage = "1"
        job = CoroutineScope(Dispatchers.Main+exceptionHandler).launch {
            try {
                loading.value = false
                bookRepository.refreshDatabase(nextPage)
                nextPage = "2"
                _eventNetworkError.value = false
                loading.value = false
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(booksList.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                    errorMessage.value = "book list is empty"
                    loading.value = false
                }
            }
        }
    }

      fun getNextPage(){
        loading.value = true
        Log.v("loading",loading.value.toString())

        job = CoroutineScope(Dispatchers.Main+exceptionHandler).launch {
            try {
                val response = bookRepository.getBooksFromRemote(nextPage)
                if (response.isSuccessful) {

                    Log.v("NEXTPageRes", response.body()?.results.toString())
                    var results = response.body()?.results

                    booksList.value = booksList.value!!.plus(results!!)

                    Log.v("ListTAdded", booksList.value!!.size.toString())

                }
                    nextPage = response.body()?.next.toString()
                    Log.v("NEXTz", nextPage)
                    nextPage = nextPage.last().toString()
                    loading.value = false

            }catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(booksList.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                    errorMessage.value = "book list is empty"
                    loading.value = false
                }

            }
        }
    }

//    fun getNextPage(){
//        loading.value = true
//        Log.v("loading",loading.value.toString())
//        job = CoroutineScope(Dispatchers.Main+exceptionHandler).launch {
//            try {
//                booksList.value = apiInterface.getNextPage(nextPage).value
//                nextPage = mainRepository.next
//                _eventNetworkError.value = false
//                loading.value = false
//            } catch (networkError: IOException) {
//                // Show a Toast error message and hide the progress bar.
//                if(booksList.value.isNullOrEmpty()) {
//                    _eventNetworkError.value = true
//                    errorMessage.value = "book list is empty"
//                    loading.value = false
//                }
//
//            }
//        }
//    }


    private fun onError(message: String) {
        Log.v("onError",message)
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    //    fun getAllBooks() {
//        loading.value = true
//        job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
//            val response = mainRepository.getAllBooks(nextPage)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    if(booksList.value.isNullOrEmpty()){
//                        Log.v("NEXT0", nextPage)
//                        booksList.postValue(response.body()?.results?.toMutableList())
//
//                    }else{
//
//                        Log.v("NEXTPageRes",response.body()?.results.toString())
//                        var results = response.body()?.results
//
//                        booksList.value = booksList.value!!.plus(results!!)
//
//                        Log.v("ListTAdded", booksList.value!!.size.toString())
//
//
//                    }
//                    nextPage = response.body()?.next.toString()
//                    Log.v("NEXTz",nextPage)
//                    nextPage = nextPage.last().toString()
//                    loading.value = false
//                    return@withContext
//                } else {
//                    onError("Error : ${response.message()} ")
//                }
//            }
//        }
//
//    }

}


