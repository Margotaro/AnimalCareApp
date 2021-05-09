package com.example.petproject2

import android.R
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.petproject2.database.VetNote


class MedicalFragmentScenarios {

    private lateinit var chronoSortScenario: ChronoSortScenario
    private lateinit var vetNoteScenario: VetNoteScenario
    private lateinit var scenarioList: Array<MedScenarioFragment>
    private var petId: Int = -1
    private var currentScenarioFragment: MedScenarioFragment? = null
    private lateinit var ft: FragmentTransaction

    fun onStart(supportFragmentManager: FragmentManager, petId: Int) {
        this.petId = petId
        chronoSortScenario = ChronoSortScenario(petId, supportFragmentManager)
        vetNoteScenario = VetNoteScenario(supportFragmentManager)
        scenarioList = arrayOf(chronoSortScenario, vetNoteScenario)

        for (scenario in scenarioList) {
            scenario.hide()
        }
        setNewActiveScenario(chronoSortScenario)
    }

    private fun setNewActiveScenario(scenario: MedScenarioFragment) {
        currentScenarioFragment?.hide()
        currentScenarioFragment = scenario
        scenario.show(petId)
    }
    fun onOpenVetNoteRequest(supportFragmentManager: FragmentManager, vetNote: VetNote) {
        chronoSortScenario.hide()
    }
    fun showChronoSort() {
        setNewActiveScenario(chronoSortScenario)
    }
    fun onResume()
    {
        currentScenarioFragment?.show(petId)
    }
}