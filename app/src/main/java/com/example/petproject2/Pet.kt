package com.example.petproject2

import android.os.Parcelable
import com.example.petproject2.database.IllnessTypeEntity
import com.example.petproject2.database.PetEntity
import com.example.petproject2.database.VetNoteEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
class Pet(var name: String,
          var species: String?,
          var breed: String?,
          var neuter: String?,
          var chip: String?,
          var vetAddress: String?,
          var notes: String?,
          var image: String?): Parcelable {
    constructor(
        pe: PetEntity): this(
                pe.name,
                pe.species,
                pe.breed,
                pe.neuter,
                pe.chip,
                pe.vetAddress,
                pe.notes,
                pe.image)

    fun updateObject(name: String, species: String?, breed: String?, neuter: String?, chip: String?, vetAddress: String?, notes: String?, image: String?) {
        this.name = name
        this.species = species
        this.breed = breed
        this.neuter = neuter
        this.chip = chip
        this.vetAddress = vetAddress
        this.notes = notes
        this.image = image
    }

    fun generatePetEntity(): PetEntity {
        return PetEntity(0, this.name, this.species, this.breed, this.neuter, this.chip, this.vetAddress, this.notes, this.image)
    }
}