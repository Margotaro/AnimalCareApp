package com.example.petproject2.database

import android.os.Parcelable
import android.text.format.DateFormat
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class VetNote(
    val id: Int,
    val petId: Int,
    var diagnosisNote: String,
    var vetName: String,
    var prescribedMedication: String
): Parcelable {
    var diagnosisDate: Long = 0
    var displayDiagnosisDate: String = ""

    init {
        diagnosisDate = getCurrentTime()
        displayDiagnosisDate = getCurrentTimeString(diagnosisDate)
    }

    constructor(
        vne: VetNoteEntity): this(
            vne.id,
            vne.petId,
            vne.diagnosisNote,
            vne.vetName,
            vne.prescribedMedication) {
                this.diagnosisDate = vne.diagnosisDate
                this.displayDiagnosisDate = getCurrentTimeString(vne.diagnosisDate)
                }

    constructor(
        id: Int,
        petId: Int,
        diagnosisNote: String,
        vetName: String,
        prescribedMedication: String,
        diagnosisDate: Long): this(
            id,
            petId,
            diagnosisNote,
            vetName,
            prescribedMedication) {
                this.diagnosisDate = diagnosisDate
                this.displayDiagnosisDate = getCurrentTimeString(diagnosisDate)
                }

    fun getCurrentTime(): Long {
        return Calendar.getInstance(Locale.ENGLISH).timeInMillis
    }
    fun getCurrentTimeString(time: Long): String {
        return DateFormat.format("dd-MM-yyyy hh:mm:ss", time).toString()
    }

    fun generateNotificationEntity(): VetNoteEntity {
        return VetNoteEntity(id, petId, diagnosisNote, vetName, prescribedMedication, diagnosisDate)
    }
}