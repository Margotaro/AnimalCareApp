package com.example.petproject2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_create_pet_page.*

class CreatePetPage : Activity() {
    private val imageLoader = ImageLoader(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pet_page)
        uploadPetAvatarButton.setOnClickListener {
            imageLoader.runRequest()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) { return }

        imageLoader.handleRequest(requestCode, resultCode, data)?.let {
            uploadPetAvatarButton.setImageBitmap(it)
        }
    }
}