package com.example.droidsum2.modelo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val repository: SumRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SumViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SumViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
