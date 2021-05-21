package com.example.petproject2

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.petproject2.database.AppDatabase
import com.example.petproject2.database.IllnessTypeEntity
import com.example.petproject2.database.VetNote
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_PET_ID = "PetId"
private const val VET_NOTE_CREATE = 1
class CreateVetNoteActivity : AppCompatActivity() {

    private lateinit var closeButton: ImageButton
    private lateinit var confirmButton: ImageButton
    private lateinit var currentTimeTextView: TextView
    private lateinit var doctorNameTextView: TextInputEditText
    private lateinit var noteTextView: TextInputEditText
    private lateinit var medicamentsTextView: TextInputEditText
    private lateinit var chipGroupIllness: ChipGroup
    private var petId: Int? = null
    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_vet_note)
        var oldSavedIntent = getIntent()
        petId = oldSavedIntent.getIntExtra(ARG_PET_ID, 0)
        database = AppDatabase.getDatabase(this)
    }
    override fun onStart() {
        super.onStart()

        doctorNameTextView = findViewById(R.id.doctorName)
        noteTextView = findViewById(R.id.vetVisitNotes)
        medicamentsTextView = findViewById(R.id.prescribedMedicaments)
        chipGroupIllness = findViewById(R.id.chipGroup2)

        closeButton = findViewById(R.id.buttonCancelVetNote)
        closeButton.setOnClickListener {
            finish()
        }

        confirmButton = findViewById(R.id.buttonConfirmVetNote)
        confirmButton.setOnClickListener {
            if(TextUtils.isEmpty(doctorNameTextView.text)){
            Toast.makeText(this, "Specify doctor name", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
            }
            if(TextUtils.isEmpty(noteTextView.text)){
                Toast.makeText(this, "Your note is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val mDoctorName = doctorNameTextView.text
            val mNote = noteTextView.text
            val mMedicaments = medicamentsTextView.text
            val mDiseases = getIllnessList(chipGroupIllness)
            val mTime = Calendar.getInstance().timeInMillis
            petId?.let {  petId ->
                mNote?.let { mNote ->
                    mDoctorName?.let { mDoctorName ->
                        mTime?.let { mTime ->
                            composeVetNote(VetNote(0, petId, mNote.toString(), mDoctorName.toString(), mMedicaments.toString(), mTime), mDiseases)
                        }
                    }
                }
            }
        }
        currentTimeTextView = findViewById(R.id.textViewVetNoteDate)
        resetTime()
    }
    fun resetTime() {
        val timeNow = Calendar.getInstance().time
        currentTimeTextView.setText(SimpleDateFormat("dd/MM/yyyy \n HH:mm:ss aaa z").format(timeNow).toString())
    }
    fun getIllnessList(cg: ChipGroup) : List<String> {
        val illnessNameArray = mutableListOf<String>()
        val count = chipGroupIllness.childCount
        var i = 0
        while (i < count) {
            val chip: Chip = cg.getChildAt(i) as Chip
            chip.text?.let {
                illnessNameArray.add(it.toString())
            }
            i++
        }
        return illnessNameArray.distinct()
    }
    fun composeVetNote(vetNote: VetNote, illList: List<String>) {
        petId?.let {
            petId ->
            var noteIdList = database.vetNoteDao().insertAll(vetNote.generateVetNoteEntity())
            val noteIdListSize = noteIdList.count()
            var noteId = noteIdList[noteIdListSize - 1].toInt()
            for (i in illList) {
                database.ilnessDao().insertAll(IllnessTypeEntity(0, noteId, i))
            }
        }
        finish()
    }
}