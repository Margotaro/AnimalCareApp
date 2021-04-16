package com.example.petproject2

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

class PetPageActivity : FragmentActivity() {

    private lateinit var documentsButton: ImageView
    private lateinit var medicalButton: ImageView
    private lateinit var alarmButton: ImageView

    private val scenarios = PetPageScenarios()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_page)
        val petNameView = findViewById<TextView>(R.id.petNameView)
        val avatarImageView = findViewById<ImageView>(R.id.petAvatarView)
        val packageName = this.packageName
        val oldSavedIntent = getIntent()
        val petId = oldSavedIntent.getIntExtra("PetId", -1)
        scenarios.onStart(supportFragmentManager, petId)
        avatarImageView.setImageResource(this.getResources().getIdentifier(oldSavedIntent.getStringExtra("Avatar"), "drawable", packageName))
        petNameView.setText(oldSavedIntent.getStringExtra("Name"))
    }

    override fun onStart() {
        super.onStart()
        documentsButton = findViewById(R.id.documentsButton)
        medicalButton = findViewById(R.id.medicalButton)
        alarmButton = findViewById(R.id.alarmButton)

        documentsButton.setOnClickListener { scenarios.showDocuments() }
        medicalButton.setOnClickListener { scenarios.showMedicine() }
        alarmButton.setOnClickListener { scenarios.showAlarm() }
    }

    override fun onPause() {
        super.onPause()

    }
    override fun onResume() {
        super.onResume()
        scenarios.onResume()
    }
}
