package com.example.petproject2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager

interface PetScenarioFragment {
    fun show(petId: Int)
    fun hide()
}

class MedicalScenario(fragmentManager: FragmentManager) : PetScenarioFragment {

    private val medicalFragment: MedicalFragment

    init {
        medicalFragment = fragmentManager.findFragmentById(R.id.medicalFragment) as MedicalFragment
    }

    override fun show(petId: Int) {
        medicalFragment.view?.visibility = View.VISIBLE
    }

    override fun hide() {
        medicalFragment.view?.visibility = View.GONE
    }
}

class DocumentScenario(fragmentManager: FragmentManager): PetScenarioFragment {

    private val documentsFragment: DocumentsFragment

    init {
        documentsFragment = fragmentManager.findFragmentById(R.id.documentFragment) as DocumentsFragment
    }
    override fun show(petId: Int) {
        documentsFragment.view?.visibility = View.VISIBLE
    }

    override fun hide() {
        documentsFragment.view?.visibility = View.GONE
    }
}

class NotificationScenario(fragmentManager: FragmentManager): PetScenarioFragment {

    private val notificationFragment: NotificationFragment

    init {
        notificationFragment = fragmentManager.findFragmentById(R.id.alarmFragment) as NotificationFragment
    }
    override fun show(petId: Int) {
        notificationFragment.view?.visibility = View.VISIBLE
        notificationFragment.show(petId)
    }

    override fun hide() {
        notificationFragment.view?.visibility = View.GONE
    }
}

