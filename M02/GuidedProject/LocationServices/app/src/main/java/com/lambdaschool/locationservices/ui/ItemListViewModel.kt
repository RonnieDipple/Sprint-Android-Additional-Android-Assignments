package com.lambdaschool.locationservices.ui

import androidx.lifecycle.ViewModel
import com.lambdaschool.locationservices.model.Contact

class ItemListViewModel : ViewModel() {

    private var savedContacts = mutableListOf<Contact>()

    fun clearSavedContacts() {
        savedContacts.clear()
    }

    fun savedContactsSize(): Int = savedContacts.size

    fun putSavedContacts(contacts: List<Contact>) {
        clearSavedContacts()
        savedContacts = contacts.toMutableList()
    }

    fun getSavedContacts(): List<Contact> = savedContacts.toList()
}
