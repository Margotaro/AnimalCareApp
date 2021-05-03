package com.example.petproject2

import android.R
import androidx.fragment.app.FragmentManager


class MedicalFragmentScenarios {

    private lateinit var chronoSortScenario: ChronoSortScenario
    private lateinit var scenarioList: Array<MedScenarioFragment>
    private var petId: Int = -1
    private var currentScenarioFragment: MedScenarioFragment? = null

    fun onStart(supportFragmentManager: FragmentManager, petId: Int) {
        this.petId = petId
        chronoSortScenario = ChronoSortScenario(supportFragmentManager, petId)

        scenarioList = arrayOf(chronoSortScenario)

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

    fun showChronoSort() {
        setNewActiveScenario(chronoSortScenario)
    }
    fun onResume()
    {
        currentScenarioFragment?.show(petId)
    }
}