package com.example.petproject2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject2.database.AppDatabase
import com.example.petproject2.database.CompleteVetNote
import com.example.petproject2.database.VetNote
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PET_ID = "PetId"
private const val ARG_INIT_ILLNESS_NAME = "IllnessName"

class MedicalFragmentFilteredSort : Fragment(), OnVetNoteChangeListener  {
    private var petId: Int? = null
    private var illnessName: String? = null
    lateinit var database: AppDatabase
    lateinit var rvMedRecords: RecyclerView
    private lateinit var parentFragment: NoteViewClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getInt(ARG_PET_ID)
            illnessName = it.getString(ARG_INIT_ILLNESS_NAME)
        }
        activity?.applicationContext?.let {
            rvMedRecords = RecyclerView(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(
            R.layout.fragment_medical_filtered_sort,
            container,
            false
        )
        rvMedRecords = rootView.findViewById<View>(R.id.rvFilteredMedicalRecords) as RecyclerView
        rvMedRecords.layoutManager = LinearLayoutManager(context)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        petId?.let{ pid -> {
            illnessName?.let { iln ->
                    showContent(pid, iln)
                }
            }
        }
    }

    fun showContent(petId: Int, illnessName: String) {
        this.petId = petId
        this.illnessName = illnessName
        context?.let { context ->
            RefreshMedRecordListView(context, illnessName)
        }
    }

    fun RefreshMedRecordListView(cnt: Context, illnessName: String) {
        rvMedRecords.adapter = VetNoteListAdapter(loadFromDatabase(cnt, illnessName), this)
    }

    fun loadFromDatabase(cnt: Context, illnessName: String): MutableList<VetNote>{

        database = AppDatabase.getDatabase(cnt)
        petId?.let { petId ->
            val medreccollection = database.vetNoteDao().findPetMedNotesByIllness(petId, illnessName)
            return medreccollection.mapNotNull {
                        completeVetNote ->
                    completeVetNote.bar?.let {
                            bar ->
                        completeVetNote.vetNote?.let {
                                vetNote ->
                            VetNote(
                                vetNote, bar
                            )
                        }
                    }
                }.toMutableList()
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
        parentFragment.onNoteClicked(vetNote)
    }
    fun setParentFragment(fragment: NoteViewClickListener){
        parentFragment = fragment
    }

    fun filterContentsByIllness(illnessName: String, cnt: Context) {
        RefreshMedRecordListView(cnt, illnessName)
    }

    companion object {
        @JvmStatic
        fun newInstance(petId: Int, illnessName: String) =
            MedicalFragmentFilteredSort().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PET_ID, petId)
                    putString(ARG_INIT_ILLNESS_NAME, illnessName)
                }
            }
    }
}