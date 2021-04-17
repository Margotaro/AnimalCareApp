package com.example.petproject2

import androidx.fragment.app.FragmentManager


class PetPageScenarios {

    private lateinit var documentsScenario: DocumentScenario
    private lateinit var medicalScenario: MedicalScenario
    private lateinit var alarmScenario: NotificationScenario
    private lateinit var habitScenario: HabitScenario
    private lateinit var ratioScenario: RatioScenario
    private lateinit var measurementsScenario: MeasurementsScenario
    private lateinit var scenarioList: Array<PetScenarioFragment>

    private var petId: Int = -1

    private var currentScenarioFragment: PetScenarioFragment? = null

    fun onStart(supportFragmentManager: FragmentManager, petId: Int) {
        this.petId = petId
        documentsScenario = DocumentScenario(supportFragmentManager)
        medicalScenario = MedicalScenario(supportFragmentManager)
        alarmScenario = NotificationScenario(supportFragmentManager)
        habitScenario = HabitScenario(supportFragmentManager)
        ratioScenario = RatioScenario(supportFragmentManager)
        measurementsScenario = MeasurementsScenario(supportFragmentManager)

        scenarioList = arrayOf(documentsScenario, medicalScenario, alarmScenario, habitScenario, ratioScenario, measurementsScenario)

        for (scenario in scenarioList) {
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