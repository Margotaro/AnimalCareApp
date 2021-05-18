package com.example.petproject2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.petproject2.database.VetNote


private const val ARG_PET_ID = "PetId"

interface NoteViewClickListener {
    fun onNoteClicked(vetNote: VetNote)
    fun onIllnessTypeClicked(illness: String)
    fun onSpinnerIllnessOptionClicked()
    fun onSpinnerChronoOptionClicked()
}

class MedicalFragment : Fragment(), NoteViewClickListener {
    private var petId: Int? = null
    private val scenarios = MedicalFragmentScenarios()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getInt(ARG_PET_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_medical, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        petId?.let{
            val petIdNonNull: Int = it
            activity?.let{
                scenarios.onStart(childFragmentManager, petIdNonNull, this)
            }
        }
    }

    override fun onNoteClicked(vetNote: VetNote) {
        scenarios.noteWasClicked(vetNote)
    }

    override fun onIllnessTypeClicked(illness: String) {
        context?.let {
            scenarios.illnessOptionWasClicked(illness, it)
        }
    }

    override fun onSpinnerIllnessOptionClicked() {
        scenarios.spinnerByIllnessWasClicked()
    }

    override fun onSpinnerChronoOptionClicked() {
        scenarios.spinnerChronologicallyWasClicked()
    }

    companion object {
        @JvmStatic
        fun newInstance(petId: Int) =
            MedicalFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PET_ID, petId)
                }
            }
    }
}