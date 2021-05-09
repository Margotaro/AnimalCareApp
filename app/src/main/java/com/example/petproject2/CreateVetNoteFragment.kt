package com.example.petproject2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petproject2.database.AppDatabase


private const val ARG_PET_ID = "PetId"

class CreateVetNoteFragment : Fragment() {
    private var petId: Int? = null
    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getInt(com.example.petproject2.ARG_PET_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_create_vet_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(petId: Int) =
                CreateVetNoteFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PET_ID, petId)
                    }
                }
    }
}