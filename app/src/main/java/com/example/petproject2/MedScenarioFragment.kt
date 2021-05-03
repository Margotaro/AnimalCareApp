package com.example.petproject2

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

interface MedScenarioFragment {
    fun show(petId: Int)
    fun hide()
}

class ChronoSortScenario(childFragmentManager: FragmentManager, petId: Int) : MedScenarioFragment {

    private var chronoSortFragment: MedicalFragmentChronologicalSort

    init {

        val ft: FragmentTransaction = childFragmentManager.beginTransaction()
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
