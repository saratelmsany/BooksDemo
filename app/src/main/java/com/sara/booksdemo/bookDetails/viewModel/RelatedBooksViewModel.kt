package com.sara.booksdemo.bookDetails.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.color.utilities.MaterialDynamicColors.onError
import com.sara.booksdemo.allBooks.pojo.BookItem
import com.sara.booksdemo.bookDetails.RelatedBooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RelatedBooksViewModel @Inject constructor(
    private val relatedBooksRepository: RelatedBooksRepository
)  :ViewModel()  {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val errorMessage = MutableLiveData<String>()
    var booksList = MutableLiveData<List<BookItem>>()
    private var _eventNetworkError = MutableLiveData(false)




    fun getRelatedBooks(author: String){
        job = CoroutineScope(Dispatchers.Main+exceptionHandler).launch {
            try {
                val response = relatedBooksRepository.getRelatedBooks(author)
                if (response.isSuccessful) {

                    var results = response.body()?.results
                  //  Log.v("NEXTPageRes", response.body()?.results.toString())

                    booksList.value = results!!
                    Log.v("ListTAdded", booksList.value.toString())
                }
            }catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(booksList.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                    errorMessage.value = "book list is empty"
                }

            }
        }
    }

    private fun onError(message: String) {
        Log.v("onError",message)
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}