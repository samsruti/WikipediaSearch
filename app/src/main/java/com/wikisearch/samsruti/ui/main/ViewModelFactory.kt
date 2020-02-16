package com.wikisearch.samsruti.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wikisearch.samsruti.repository.WikiRepository

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: WikiRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WikiPagesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WikiPagesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
