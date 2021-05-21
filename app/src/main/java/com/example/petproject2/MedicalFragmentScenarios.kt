package com.example.petproject2

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.petproject2.database.VetNote


class MedicalFragmentScenarios {

    private lateinit var chronoSortScenario: ChronoSortScenario
    private lateinit var vetNoteScenario: VetNoteScenario
    private lateinit var illnessListScenario: IllnessListScenario
    private lateinit var scenarioList: Array<MedScenarioFragment>
    private lateinit var filteredVetNotes: MedicalFragmentFilteredSortScenario
    private var petId: Int = -1
    private var currentScenarioFragment: MedScenarioFragment? = null
    private lateinit var ft: FragmentTransaction

    fun onStart(supportFragmentManager: FragmentManager, petId: Int, parentFragment: NoteViewClickListener) {
        this.petId = petId
        illnessListScenario = IllnessListScenario(petId, supportFragmentManager)
        illnessListScenario.setParentFragment(parentFragment)
        chronoSortScenario = ChronoSortScenario(petId, supportFragmentManager)
        chronoSortScenario.setParentFragment(parentFragment)
        vetNoteScenario = VetNoteScenario(supportFragmentManager)
        filteredVetNotes = MedicalFragmentFilteredSortScenario(petId, supportFragmentManager)
        filteredVetNotes.setParentFragment(parentFragment)

        scenarioList = arrayOf(chronoSortScenario, vetNoteScenario, illnessListScenario, filteredVetNotes)

        setNewActiveScenario(chronoSortScenario)
    }

    private fun setNewActiveScenario(scenario: MedScenarioFragment) {
        currentScenarioFragment = scenario
        scenario.replace(petId)
    }
    fun noteWasClicked(vetNote: VetNote) {
        vetNoteScenario.setContents(vetNote)
        setNewActiveScenario(vetNoteScenario)
    }
    fun spinnerChronologicallyWasClicked() {
        chronoSortScenario.refreshContents()
        setNewActiveScenario(chronoSortScenario)
    }
    fun spinnerByIllnessWasClicked() {
        setNewActiveScenario(illnessListScenario)
    }
    fun illnessOptionWasClicked(illnessName: String, cnt: Context) {
        setNewActiveScenario(filteredVetNotes)
        filteredVetNotes.filterContentsByIllness(illnessName, cnt)
    }
}