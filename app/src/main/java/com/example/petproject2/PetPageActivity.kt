package com.example.petproject2

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_pet_page.*

class PetPageActivity : FragmentActivity() {

    private lateinit var documentsButton: ImageView
    private lateinit var medicalButton: ImageView
    private lateinit var alarmButton: ImageView

    private lateinit var documentsScenario: DocumentScenario
    private lateinit var medicalScenario: MedicalScenario
    private lateinit var alarmScenario: NotificationScenario

    private var currentScenarioFragment: PetScenarioFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_page)
        var oldSavedIntent = getIntent()
        var petNameView = findViewById<TextView>(R.id.petNameView)
        var avatarImageView = findViewById<ImageView>(R.id.petAvatarView)
        avatarImageView.setImageResource(oldSavedIntent.getStringExtra("Avatar").toInt())
        petNameView.setText(oldSavedIntent.getStringExtra("Name"))
    }

    override fun onStart() {
        super.onStart()
        documentsScenario = DocumentScenario(supportFragmentManager)
        medicalScenario = MedicalScenario(supportFragmentManager)
        alarmScenario = NotificationScenario(supportFragmentManager)
        for (scenario in arrayOf(documentsScenario, medicalScenario, alarmScenario)) {
            scenario.hide()
        }
        documentsButton = findViewById(R.id.documentsButton)
        medicalButton = findViewById(R.id.medicalButton)
        alarmButton = findViewById(R.id.alarmButton)
        documentsButton.setOnClickListener {
            setNewActiveScenario(documentsScenario)
        }
        medicalButton.setOnClickListener {
            setNewActiveScenario(medicalScenario)
        }
        alarmButton.setOnClickListener {
            setNewActiveScenario(alarmScenario)
        }
    }

    private fun setNewActiveScenario(scenario: PetScenarioFragment) {
        currentScenarioFragment?.hide()
        currentScenarioFragment = scenario
        scenario.show()
    }
}

