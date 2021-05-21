package com.example.petproject2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.example.petproject2.database.VetNote


class VetNoteFragment : Fragment() {
    private var vetNote: VetNote? = null
    private lateinit var vetNoteTimeTextView: TextView
    private var vetNoteTimeText: String = "Time"
    private lateinit var vetNoteDoctorNameTextView: TextView
    private var vetNoteDoctorNameText: String = "Doctor Name"
    private lateinit var vetNoteTextView: TextView
    private var vetNoteText: String = "Note contents"
    private lateinit var vetNoteMedPrescriptionTextView: TextView
    private var vetNoteMedPrescriptionText: String = "Medications"
    private lateinit var vetNoteDiseaseListTextView: TextView
    private var vetNoteDiseaseListText: String = "Disease"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_vet_note, container, false)
        vetNoteTimeTextView = root.findViewById<TextView>(R.id.vetNoteTimeText)
        vetNoteDoctorNameTextView = root.findViewById<TextView>(R.id.vetNoteDoctorNameText)
        vetNoteTextView = root.findViewById<TextView>(R.id.vetNoteText)
        vetNoteMedPrescriptionTextView = root.findViewById<TextView>(R.id.vetNotePrescribedMedText)
        vetNoteDiseaseListTextView = root.findViewById(R.id.textDisease)

        vetNoteTimeTextView.setText(vetNoteTimeText)
        vetNoteDoctorNameTextView.setText(vetNoteDoctorNameText)
        vetNoteTextView.setText(vetNoteText)
        vetNoteMedPrescriptionTextView.setText(vetNoteMedPrescriptionText)
        vetNoteDiseaseListTextView.setText(vetNoteDiseaseListText)
        return root
    }

    fun setContents(vetNote: VetNote) {
        vetNoteTimeText = vetNote.displayDiagnosisDate
        vetNoteDoctorNameText = vetNote.vetName
        vetNoteText = vetNote.diagnosisNote
        var prscrM = ""
        vetNote.prescribedMedication?.let {
            prscrM = it
        }
        vetNoteMedPrescriptionText = prscrM
        vetNoteDiseaseListText = vetNote.getIllnessBarString()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            VetNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}