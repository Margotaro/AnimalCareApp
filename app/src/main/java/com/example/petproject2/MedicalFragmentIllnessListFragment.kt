package com.example.petproject2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject2.database.AppDatabase


interface IllnessOptionClickListener {
    fun onItemClicked(illnessName: String)
}
private const val ARG_PET_ID = "PetId"

class MedicalFragmentIllnessListFragment : Fragment(), IllnessOptionClickListener {
    private var petId: Int? = null
    private lateinit var parentFragment: NoteViewClickListener
    lateinit var database: AppDatabase
    lateinit var rvMedRecords: RecyclerView
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getInt(ARG_PET_ID)
        }
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (parentFragmentManager.backStackEntryCount == 1 || parentFragmentManager.backStackEntryCount == 0)
                    parentFragmentManager.popBackStack()
                else
                    activity?.finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_medical_illness_list, container, false)
        rvMedRecords = rootView.findViewById<View>(R.id.rvIllnessTypeRecords) as RecyclerView
        spinner = rootView.findViewById(R.id.spinnerSortMedRecords2)
        rvMedRecords.layoutManager = LinearLayoutManager(context)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        petId?.let{
            showContent(it)
        }
        //spinner.isSelected = false
        //spinner.setSelection(0, false)
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 1) {
                    parentFragment.onSpinnerChronoOptionClicked()
                    spinner.setSelection(0)
                }
            }

        }
        spinner.post { spinner.onItemSelectedListener = listener }
    }

    override fun onSaveInstanceState(outState: Bundle) { }

    fun setParentFragment(fragment: NoteViewClickListener){
        parentFragment = fragment
    }

    fun showContent(petId: Int) {
        this.petId = petId
        RefreshIllnessRecordListView()
    }

    fun RefreshIllnessRecordListView() {
        rvMedRecords.adapter = StringListAdapter(loadFromDatabase(), this)
    }

    fun loadFromDatabase(): MutableList<String>{
        context?.let {
            database = AppDatabase.getDatabase(it)
            var illreccollection = mutableListOf<String>()
            petId?.let {
                illreccollection = database.ilnessDao().getAllDistinct(it).toMutableList()
            }
            return illreccollection
        }
        return mutableListOf()
    }

    companion object {
        @JvmStatic
        fun newInstance(petId: Int) =
            MedicalFragmentIllnessListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PET_ID, petId)
                }
            }
    }

    override fun onItemClicked(illnessName: String) {
        parentFragment.onIllnessTypeClicked(illnessName)
    }
}