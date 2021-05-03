package com.example.petproject2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject2.database.AppDatabase
import com.example.petproject2.database.VetNote
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_PET_ID = "PetId"

class MedicalFragmentChronologicalSort : Fragment(), OnVetNoteChangeListener  {
    private var petId: Int? = null
    lateinit var database: AppDatabase
    lateinit var rvMedRecords: RecyclerView
    lateinit var addMedRecordButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getInt(com.example.petproject2.ARG_PET_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_medical_chronological_sort, container, false)
        rvMedRecords = rootView.findViewById<View>(R.id.rvMedicalRecords) as RecyclerView
        addMedRecordButton = rootView.findViewById(R.id.buttonAddMedRecord)
        rvMedRecords.layoutManager = LinearLayoutManager(context)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        petId?.let{
            showContent(it)
        }
        addMedRecordButton?.setOnClickListener {
            val intent = Intent(context, BlankFragment::class.java)
            intent.putExtra("PetId", petId)
            startActivity(intent)
        }
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

    override fun onItemClicked(vetNote: VetNote) {
/*
        val confirmDeleteDialog = ConfirmNotificationDeleteDialogFragment()
        confirmDeleteDialog.setTargetFragment(this, 0)
        val bundle = Bundle()
        bundle.putParcelable("Alarm", alarm)
        confirmDeleteDialog.arguments = bundle
        confirmDeleteDialog.show(parentFragmentManager, "dialog")*/
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