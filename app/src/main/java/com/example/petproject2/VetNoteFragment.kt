package com.example.petproject2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.petproject2.database.VetNote


class VetNoteFragment : Fragment() {
    private var vetNote: VetNote? = null
    private lateinit var vetNoteTimeTextView: TextView
    private lateinit var vetNoteDoctorNameTextView: TextView
    private lateinit var vetNoteTextView: TextView
    private lateinit var vetNoteMedPrescriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_vet_note, container, false)
        val vetNoteTimeTextView = root.findViewById<TextView>(R.id.vetNoteTimeText)
        val vetNoteDoctorNameTextView = root.findViewById<TextView>(R.id.vetNoteDoctorNameText)
        val vetNoteTextView = root.findViewById<TextView>(R.id.vetNoteText)
        val vetNoteMedPrescriptionTextView = root.findViewById<TextView>(R.id.vetNotePrescribedMedText)

        return root
    }

    fun SetContents(vetNote: VetNote) {
        vetNoteTimeTextView.setText(vetNote.displayDiagnosisDate)
        vetNoteDoctorNameTextView.setText(vetNote.vetName)
        vetNoteTextView.setText(vetNote.diagnosisNote)
        vetNoteMedPrescriptionTextView.setText(vetNote.prescribedMedication)
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