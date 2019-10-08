package com.lambdaschool.locationservices.arch

import androidx.lifecycle.ViewModelProvider

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory()
    }
}