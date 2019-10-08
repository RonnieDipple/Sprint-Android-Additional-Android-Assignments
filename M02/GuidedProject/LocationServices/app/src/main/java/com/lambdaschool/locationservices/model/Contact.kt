package com.lambdaschool.locationservices.model

import java.io.Serializable


// TODO: S09M02-5b add Serializable implementation to pass object

data class ContactName(val title: String, val first: String, val last: String) : Serializable

data class ContactPicture(val large: String, val medium: String, val thumbnail: String) :
    Serializable

data class ContactLocationStreet(val number: Int, val name: String) : Serializable

data class ContactLocationCoordinates(val latitude: Double, val longitude: Double) : Serializable

// TODO: S09M02-5a add location fields to contact
data class ContactLocation(
    val street: ContactLocationStreet,
    val city: String,
    val state: String,
    val postcode: String,
    val coordinates: ContactLocationCoordinates
) : Serializable


// TODO: S09M02-6 Define contact fields for json
data class Contact(
    val name: ContactName,
    val email: String,
    val phone: String,
    val picture: ContactPicture,
    val location: ContactLocation
) : Serializable {
    fun getFullName(): String = "${name.title} ${name.first} ${name.last}"
}

data class ContactResults(val results: List<Contact>) : Serializable
