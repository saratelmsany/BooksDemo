package com.sara.booksdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sara.booksdemo.viewModel.BookViewModel

class MyViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            BookViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
