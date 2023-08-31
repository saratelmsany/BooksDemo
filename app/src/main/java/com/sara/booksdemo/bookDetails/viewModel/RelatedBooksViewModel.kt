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

    val loading = MutableLiveData<Boolean>()



    fun getRelatedBooks(author: String){
        loading.value = true
        job = CoroutineScope(Dispatchers.Main+exceptionHandler).launch {
            try {
                val response = relatedBooksRepository.getRelatedBooks(author)
                if (response.isSuccessful) {

                    var results = response.body()?.results

                    booksList.value = results!!
                }
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

    private fun onError(message: String) {
        Log.v("onError",message)
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}