package com.example.petproject2

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.petproject2.DocumentScenario
import com.example.petproject2.MedicalScenario
import com.example.petproject2.NotificationScenario
import com.example.petproject2.PetScenarioFragment

class PetPageScenarios {

    private lateinit var documentsScenario: DocumentScenario
    private lateinit var medicalScenario: MedicalScenario
    private lateinit var alarmScenario: NotificationScenario
    private var petId: Int = -1

    private var currentScenarioFragment: PetScenarioFragment? = null

    fun onStart(supportFragmentManager: FragmentManager, petId: Int) {
        this.petId = petId
        documentsScenario = DocumentScenario(supportFragmentManager)
        medicalScenario = MedicalScenario(supportFragmentManager)
        alarmScenario = NotificationScenario(supportFragmentManager)
        for (scenario in arrayOf(documentsScenario, medicalScenario, alarmScenario)) {
            scenario.hide()
        }
        setNewActiveScenario(documentsScenario)
    }

    private fun setNewActiveScenario(scenario: PetScenarioFragment) {
        currentScenarioFragment?.hide()
        currentScenarioFragment = scenario
        scenario.show(petId)
    }

    fun showDocuments() {
        setNewActiveScenario(documentsScenario)
    }

    fun showMedicine() {
        setNewActiveScenario(medicalScenario)
    }

    fun showAlarm() {
        setNewActiveScenario(alarmScenario)
    }

    fun onResume()
    {
        currentScenarioFragment?.show(petId)
    }
}