package com.example.petproject2
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Account(var name: String, var email: String, var telephone_number: String, var avatar: Int):
    Parcelable {
    var pets: MutableList<Pet> = mutableListOf()
    fun findPet(name: String): Pet {
        return searchPetsFor(name)
    }
    private fun searchPetsFor(id: String): Pet {
        return pets.first { it.name == id }
    }
    fun updateObject(name: String, email: String, telephone_number: String) {
        this.name = name
        this.email = email
        this.telephone_number = telephone_number
    }
}