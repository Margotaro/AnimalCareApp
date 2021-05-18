package com.example.petproject2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = VetNoteEntity::class, parentColumns = arrayOf("id"), childColumns = arrayOf("vetNoteId"), onDelete = ForeignKey.CASCADE)))
data class IllnessTypeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "vetNoteId") val vetNoteId: Int,
    @ColumnInfo(name = "illnessName") val illnessName: String
)
