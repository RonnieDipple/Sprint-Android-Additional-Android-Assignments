package com.lambdaschool.locationservices.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambdaschool.locationservices.ui.ItemListViewModel

/**
 * Factory for ViewModels
 */
class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ItemListViewModel::class.java) -> ItemListViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}