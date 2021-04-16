package com.example.petproject2.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
data class PetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "species") var species: String,
    @ColumnInfo(name = "breed")  var breed: String,
    @ColumnInfo(name = "neuter")  var neuter: String,
    @ColumnInfo(name = "chip")  var chip: String,
    @ColumnInfo(name = "vetAddress")  var vetAddress: String,
    @ColumnInfo(name = "notes")  var notes: String,
    @ColumnInfo(name = "image")  var image: String
)