package com.example.petproject2

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton

private const val VET_NOTE_CREATE = 1
class CreateVetNoteActivity : AppCompatActivity() {

    private lateinit var closeButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_vet_note)
    }
    override fun onStart() {
        super.onStart()

        closeButton = findViewById(R.id.buttonCancelVetNote)
        closeButton.setOnClickListener {
            finish()
        }
    }

}