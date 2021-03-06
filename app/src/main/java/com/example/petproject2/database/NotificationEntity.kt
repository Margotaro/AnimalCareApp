package com.example.petproject2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotificationEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "petId") val petId: Int,
    @ColumnInfo(name = "notificationMsg") var notificationMsg: String,
    @ColumnInfo(name = "callTimestamp") var callTimestamp: Long,
    @ColumnInfo(name = "isOn") var isOn: Boolean
)