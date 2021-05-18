package com.example.petproject2

import android.os.Parcelable
import com.example.petproject2.database.IllnessTypeEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
class Illness( val id: Int,
               val vetNoteId: Int,
               val illnessName: String) : Parcelable {
    constructor(ite: IllnessTypeEntity) : this (
                ite.id,
                ite.vetNoteId,
                ite.illnessName)
}
/*
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "vetNoteId") val vetNoteId: Int,
    @ColumnInfo(name = "illnessName") val illnessName: String*/