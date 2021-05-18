package com.example.petproject2

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.petproject2.database.VetNote

private const val TAG = "SpinnerBugDebug"


interface MedScenarioFragment {
    fun replace(petId: Int)
}
//Implement: chronologically sorted(petId), llness list(petId), sorted by illness(petId, illness), view note(noteId)
class ChronoSortScenario(petId: Int, cf: FragmentManager) : MedScenarioFragment {

    private var chronoSortFragment: MedicalFragmentChronologicalSort
    private var cf: FragmentManager

    init {
        this.cf = cf
        val ft = cf.beginTransaction()
        chronoSortFragment = MedicalFragmentChronologicalSort.newInstance(petId)
        ft.add(R.id.fragment_chrono_sort_container, chronoSortFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack("chronological add")
        ft.setReorderingAllowed(true)
        ft.commit()
        Log.i("TAG", "Chrono fragment was created")
    }

    override fun replace(petId: Int) {
        val ft = cf.beginTransaction()
        cf.popBackStack()
        ft.replace(R.id.fragment_chrono_sort_container, chronoSortFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack("chronological replace")
        ft.setReorderingAllowed(true)
        ft.commit()
        Log.i("TAG", "Chrono fragment has replaced")
    }

    fun setParentFragment(fragment: NoteViewClickListener) {
        chronoSortFragment.setParentFragment(fragment)
    }

    fun refreshContents() {
        chronoSortFragment.RefreshMedRecordListView()
    }
}

class VetNoteScenario(cf: FragmentManager) : MedScenarioFragment {

    private var vetNoteFragment: VetNoteFragment
    private var cf: FragmentManager
    init {
        this.cf = cf
        vetNoteFragment = VetNoteFragment.newInstance()
    }

    override fun replace(petId: Int) {
        val ft = cf.beginTransaction()
        cf.popBackStack()
        ft.replace(R.id.fragment_chrono_sort_container, vetNoteFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack("Vet Note has replaced")
        ft.setReorderingAllowed(true)
        ft.commit()
    }

    fun setContents(vetNote: VetNote) {
        vetNoteFragment.setContents(vetNote)
    }
}

class IllnessListScenario(petId: Int, cf: FragmentManager) : MedScenarioFragment {

    private var illnessListFragment: MedicalFragmentIllnessListFragment
    private var cf: FragmentManager

    init {
        this.cf = cf
        illnessListFragment = MedicalFragmentIllnessListFragment.newInstance(petId)
        val ft = cf.beginTransaction()
        ft.replace(R.id.fragment_chrono_sort_container, illnessListFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack("illness fragment add")
        ft.setReorderingAllowed(true)
        ft.commit()
        Log.i("TAG", "Illness fragment was created")
    }

    override fun replace(petId: Int) {
        val ft = cf.beginTransaction()
        cf.popBackStack()
        ft.replace(R.id.fragment_chrono_sort_container, illnessListFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack("Illness fragment has replaced")
        ft.setReorderingAllowed(true)
        ft.commit()
        Log.i("TAG", "Illness fragment has replaced")
    }

    fun setParentFragment(fragment: NoteViewClickListener) {
        illnessListFragment.setParentFragment(fragment)
    }
}

class MedicalFragmentFilteredSortScenario(petId: Int, cf: FragmentManager) : MedScenarioFragment {
    private var filteredNotesFragment: MedicalFragmentFilteredSort
    private var cf: FragmentManager

    init {
        this.cf = cf
        filteredNotesFragment = MedicalFragmentFilteredSort.newInstance(petId, "")
        val ft = cf.beginTransaction()
        ft.replace(R.id.fragment_chrono_sort_container, filteredNotesFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack("Illness fragment add")
        ft.setReorderingAllowed(true)
        ft.commit()
        //Log.i("TAG", "Filtered fragment was created")
    }

    override fun replace(petId: Int) {
        val ft = cf.beginTransaction()
        cf.popBackStack()
        ft.replace(R.id.fragment_chrono_sort_container, filteredNotesFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack("Filtered fragment has replaced")
        ft.setReorderingAllowed(true)
        ft.commit()
        Log.i("TAG", "Filtered fragment has replaced")
    }

    fun setParentFragment(fragment: NoteViewClickListener) {
        filteredNotesFragment.setParentFragment(fragment)
    }

    fun filterContentsByIllness(illnessName: String, cnt: Context) {
        filteredNotesFragment.filterContentsByIllness(illnessName, cnt)
    }
}