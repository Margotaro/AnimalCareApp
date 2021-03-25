package com.example.petproject2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Pet(var name: String,
          var species: String,
          var breed: String,
          var neuter: String,
          var chip: String,
          var vetAddress: String,
          var notes: String,
          var image: Int): Parcelable {
    fun updateObject(name: String, species: String, breed: String, neuter: String, chip: String, vetAddress: String, notes: String, image: Int) {
        this.name = name
        this.species = species
        this.breed = breed
        this.neuter = neuter
        this.chip = chip
        this.vetAddress = vetAddress
        this.notes = notes
        this.image = image

    }
}