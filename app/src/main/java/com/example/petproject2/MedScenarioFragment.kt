package com.example.petproject2

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.petproject2.database.VetNote

interface MedScenarioFragment {
    fun show(petId: Int)
    fun hide()
}
//Implement: chronologically sorted(petId), llness list(petId), sorted by illness(petId, illness), view note(noteId)
class ChronoSortScenario(petId: Int, cf: FragmentManager) : MedScenarioFragment {

    private var chronoSortFragment: MedicalFragmentChronologicalSort

    init {
        val ft = cf.beginTransaction()
        chronoSortFragment = MedicalFragmentChronologicalSort.newInstance(petId)
        ft.add(R.id.fragment_chrono_sort_container, chronoSortFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun show(petId: Int) {
        chronoSortFragment.view?.visibility = View.VISIBLE
    }

    override fun hide() {
        chronoSortFragment.view?.visibility = View.GONE
    }
}

class VetNoteScenario(cf: FragmentManager) : MedScenarioFragment {

    private var vetNoteFragment: VetNoteFragment

    init {
        val ft = cf.beginTransaction()
        vetNoteFragment = VetNoteFragment.newInstance()
        ft.add(R.id.fragment_chrono_sort_container, vetNoteFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun show(petId: Int) {
        vetNoteFragment.view?.visibility = View.VISIBLE
    }

    override fun hide() {
        vetNoteFragment.view?.visibility = View.GONE
    }
}
