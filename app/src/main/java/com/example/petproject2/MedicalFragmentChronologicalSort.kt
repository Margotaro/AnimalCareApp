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
private const val VET_NOTE_CREATE = 1

class MedicalFragmentChronologicalSort : Fragment(), OnVetNoteChangeListener  {
    private var petId: Int? = null
    lateinit var database: AppDatabase
    lateinit var rvMedRecords: RecyclerView
    lateinit var addMedRecordButton: FloatingActionButton

    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var parentFragment: NoteViewClickListener
    private lateinit var spinner: Spinner

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
        spinner = rootView.findViewById(R.id.spinnerSortMedRecords)

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
        addMedRecordButton.setOnClickListener {
            context?.let {
                val intent = Intent(context, CreateVetNoteActivity::class.java)
                intent.putExtra("PetId", petId)
                startActivityForResult(intent, VET_NOTE_CREATE)
            }
        }

        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 1) {
                    parentFragment.onSpinnerIllnessOptionClicked()  // TODO
                    spinner.setSelection(0)
                }
            }

        }
        spinner.post { spinner.onItemSelectedListener = listener }
    }

    override fun onSaveInstanceState(outState: Bundle) { }

    fun showContent(petId: Int) {
        this.petId = petId
        RefreshMedRecordListView(context)
    }

    fun RefreshMedRecordListView(cnt: Context? = null) {
        rvMedRecords.adapter = VetNoteListAdapter(loadFromDatabase(cnt), this)
    }

    fun loadFromDatabase(cnt: Context? = null): MutableList<VetNote>{
        cnt?.let {
            database = AppDatabase.getDatabase(it)
            petId?.let {
                petId ->
                val medreccollection = database.vetNoteDao().findPetMedNotes(petId)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DIALOG_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                val t = data?.getParcelableExtra<VetNote>("VetNote")
                val y = t?.generateVetNoteEntity()
                data?.getParcelableExtra<VetNote>("VetNote")?.generateVetNoteEntity()?.let {
                    vetnote ->
                    database.vetNoteDao().delete(vetnote)
                    RefreshMedRecordListView()
                }
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