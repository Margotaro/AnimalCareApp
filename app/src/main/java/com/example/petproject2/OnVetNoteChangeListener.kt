package com.example.petproject2

import com.example.petproject2.database.VetNote

interface OnVetNoteChangeListener {
    fun onItemClicked(vetNote: VetNote)
}
