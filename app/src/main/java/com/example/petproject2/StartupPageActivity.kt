package com.example.petproject2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class StartupPageActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn_signIn = findViewById<Button>(R.id.button_signin)
        btn_signIn.setOnClickListener {
            Model.db.signIn("login", "password")
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
        var btn_register = findViewById<Button>(R.id.button_register)
        btn_register.setOnClickListener {
                startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}

