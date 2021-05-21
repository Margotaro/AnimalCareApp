package com.example.petproject2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = PetEntity::class, parentColumns = arrayOf("id"), childColumns = arrayOf("petId"), onDelete = ForeignKey.CASCADE)))
data class VetNoteEntity(
        @PrimaryKey(autoGenerate = true) val id: Int,
        @ColumnInfo(name = "petId") val petId: Int,
        @ColumnInfo(name = "diagnosisNote") var diagnosisNote: String,
        @ColumnInfo(name = "vetName") var vetName: String,
        @ColumnInfo(name = "prescribedMedication") var prescribedMedication: String?,
        @ColumnInfo(name = "diagnosisDate") var diagnosisDate: Long
        )
