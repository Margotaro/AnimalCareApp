package com.example.petproject2

import android.view.View
import androidx.fragment.app.FragmentManager

interface PetScenarioFragment {
    fun show(petId: Int)
    fun hide()
}

class MedicalScenario(fragmentManager: FragmentManager) : PetScenarioFragment {

    private val medicalFragment: MedicalFragment = MedicalFragment()

    init {
        //medicalFragment = fragmentManager.findFragmentById(R.id.medicalFragment) as MedicalFragment
    }

    override fun show(petId: Int) {
        medicalFragment.view?.visibility = View.VISIBLE
    }

    override fun hide() {
        medicalFragment.view?.visibility = View.GONE
    }
}

class DocumentScenario(fragmentManager: FragmentManager): PetScenarioFragment {

    private val documentsFragment: DocumentsFragment = DocumentsFragment()

    init {
        //documentsFragment = fragmentManager.findFragmentById(R.id.documentFragment) as DocumentsFragment
    }
    override fun show(petId: Int) {
        documentsFragment.view?.visibility = View.VISIBLE
    }

    override fun hide() {
        documentsFragment.view?.visibility = View.GONE
    }
}

class NotificationScenario(fragmentManager: FragmentManager): PetScenarioFragment {

    private val notificationFragment: NotificationFragment = NotificationFragment()

    init {
        //notificationFragment = fragmentManager.findFragmentById(R.id.alarmFragment) as NotificationFragment
    }
    override fun show(petId: Int) {
        notificationFragment.view?.visibility = View.VISIBLE
        notificationFragment.showContent(petId)
    }

    override fun hide() {
        notificationFragment.view?.visibility = View.GONE
    }
}

class HabitScenario(fragmentManager: FragmentManager): PetScenarioFragment {

    private val habitFragment: HabitFragment = HabitFragment()

    init {
        //habitFragment = fragmentManager.findFragmentById(R.id.habitFragment) as HabitFragment
    }
    override fun show(petId: Int) {
        habitFragment.view?.visibility = View.VISIBLE
    }

    override fun hide() {
        habitFragment.view?.visibility = View.GONE
    }
}

class RatioScenario(fragmentManager: FragmentManager): PetScenarioFragment {

    private val ratioFragment: RatioFragment = RatioFragment()

    init {
        //ratioFragment = fragmentManager.findFragmentById(R.id.ratioFragment) as RatioFragment
    }
    override fun show(petId: Int) {
        ratioFragment.view?.visibility = View.VISIBLE
    }

    override fun hide() {
        ratioFragment.view?.visibility = View.GONE
    }
}

class MeasurementsScenario(fragmentManager: FragmentManager): PetScenarioFragment {

    private val measurementFragment: MeasurementsFragment = MeasurementsFragment()

    init {
        //measurementFragment = fragmentManager.findFragmentById(R.id.measurementsFragment) as MeasurementsFragment
    }
    override fun show(petId: Int) {
        measurementFragment.view?.visibility = View.VISIBLE
    }

    override fun hide() {
        measurementFragment.view?.visibility = View.GONE
    }
}