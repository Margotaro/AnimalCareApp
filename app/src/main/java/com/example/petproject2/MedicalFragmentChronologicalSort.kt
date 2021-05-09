package com.example.petproject2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject2.database.AppDatabase
import com.example.petproject2.database.VetNote
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PET_ID = "PetId"
private const val VET_NOTE_CREATE = 1

class MedicalFragmentChronologicalSort : Fragment(), OnVetNoteChangeListener  {
    private var petId: Int? = null
    lateinit var database: AppDatabase
    lateinit var rvMedRecords: RecyclerView
    lateinit var addMedRecordButton: FloatingActionButton

    private lateinit var customAlertDialogView : View
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var doctorNameField: TextInputEditText
    private lateinit var vetVisitNotesField: TextInputEditText
    private lateinit var prescribedMedicamentsField: TextInputEditText
    private lateinit var diseaseChipGroup: FragmentContainerView
    private lateinit var currentDate: TextView

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
        val rootView = inflater.inflate(
            R.layout.fragment_medical_chronological_sort,
            container,
            false
        )
        rvMedRecords = rootView.findViewById<View>(R.id.rvMedicalRecords) as RecyclerView
        addMedRecordButton = rootView.findViewById(R.id.buttonAddMedRecord)
        rvMedRecords.layoutManager = LinearLayoutManager(context)
        context?.let {
            materialAlertDialogBuilder = MaterialAlertDialogBuilder(it)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        petId?.let{
            showContent(it)
        }
        addMedRecordButton?.setOnClickListener {
            context?.let {
                val intent = Intent(context, CreateVetNoteActivity::class.java)
                intent.putExtra("PetId", petId)
                startActivityForResult(intent, VET_NOTE_CREATE)
            }
        }
    }

    private fun launchCustomAlertDialog() {
        doctorNameField = customAlertDialogView.findViewById(R.id.doctorName)
        vetVisitNotesField = customAlertDialogView.findViewById(R.id.vetVisitNotes)
        prescribedMedicamentsField = customAlertDialogView.findViewById(R.id.prescribedMedicaments)
        diseaseChipGroup = customAlertDialogView.findViewById(R.id.fragment_disease_chip_container_view)

        val diseaseChipFragmentInstance = EntryChipDiseaseFragment.newInstance()
        childFragmentManager.beginTransaction().add(R.id.fragment_disease_chip_container_view, diseaseChipFragmentInstance).commit()

        val date_n: String = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
        // Building the Alert dialog using materialAlertDialogBuilder instance
        materialAlertDialogBuilder.setView(customAlertDialogView)
            .setTitle("New vet note")
            .setMessage(date_n)
            .setPositiveButton("Add") { dialog, _ ->
                val doctorName = doctorNameField.text.toString()
                val vetVisitNotes = vetVisitNotesField.text.toString()
                val prescribedMedicaments = prescribedMedicamentsField.text.toString()

                /**
                 * Do as you wish with the data here --
                 * Download/Clone the repo from my Github to see the entire implementation
                 * using the link provided at the end of the article.
                 */

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    fun showContent(petId: Int) {
        this.petId = petId
        RefreshMedRecordListView()
    }

    fun RefreshMedRecordListView() {
        rvMedRecords.adapter = VetNoteListAdapter(loadFromDatabase(), this)
    }

    fun loadFromDatabase(): MutableList<VetNote>{
        context?.let {
            database = AppDatabase.getDatabase(it)
            petId?.let {
                val medreccollection = database.vetNoteDao().findPetMedNotes(it)
                return medreccollection.map{ vetNoteEntity -> VetNote(
                    vetNoteEntity
                )
                }.toMutableList()
            }
            return mutableListOf()
        }
        return mutableListOf()
    }

    override fun onItemLongClicked(vetNote: VetNote) {
        val confirmDeleteDialog = ConfirmVetNoteDeleteDialogFragment()
        confirmDeleteDialog.setTargetFragment(this, 0)
        val bundle = Bundle()
        bundle.putParcelable("Note", vetNote)
        confirmDeleteDialog.arguments = bundle
        confirmDeleteDialog.show(parentFragmentManager, "dialog")
    }

    override fun onItemClicked(vetNote: VetNote) {

        val ft: FragmentTransaction = childFragmentManager.beginTransaction()
        val createVetNoteFragment = VetNoteFragment.newInstance()
        ft.add(R.id.fragment_chrono_sort_container, createVetNoteFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack(null)
        ft.commit()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == VET_NOTE_CREATE) {
            database.let {
                if(petId == null) return@let
                //TODO: refresh note list
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(petId: Int) =
            MedicalFragmentChronologicalSort().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PET_ID, petId)
                }
            }
    }
}