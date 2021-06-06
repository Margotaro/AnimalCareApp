package com.example.petproject2

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.example.petproject2.database.AppDatabase
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_create_pet_page.*


class CreatePetPage : Activity() {
    private val imageLoader = ImageLoader(this)
    private lateinit var petNameEditText: EditText
    private lateinit var speciesEditText: EditText
    private lateinit var breedEditText: EditText
    private lateinit var neuterEditText: EditText
    private lateinit var chipEditText: EditText
    private lateinit var vetAddressEditText: EditText
    private lateinit var notesEditText: TextInputEditText
    private lateinit var avatarImageView: ImageView
    private lateinit var createPetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pet_page)
        uploadPetAvatarButton.setOnClickListener {
            imageLoader.runRequest()
        }
    }

    override fun onStart() {
        super.onStart()
        petNameEditText = findViewById(R.id.editTextTextPersonName7)
        speciesEditText = findViewById(R.id.editTextTextPersonName8)
        breedEditText = findViewById(R.id.editTextTextPersonName9)
        neuterEditText = findViewById(R.id.editTextTextPersonName10)
        chipEditText = findViewById(R.id.editTextTextPersonName11)
        vetAddressEditText = findViewById(R.id.editTextTextPersonName12)
        notesEditText = findViewById(R.id.textInputEditText)
        avatarImageView = findViewById(R.id.uploadPetAvatarButton)
        createPetButton = findViewById(R.id.create_pet_button)

        createPetButton.setOnClickListener {
            if (TextUtils.isEmpty(petNameEditText.text)) {
                Toast.makeText(this, "Fill in pet name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val uriString = imageLoader.getImageUriString()
            val pet = Pet(
                petNameEditText.text.toString(),
                speciesEditText.text.toString(),
                breedEditText.text.toString(),
                neuterEditText.text.toString(),
                chipEditText.text.toString(),
                vetAddressEditText.text.toString(),
                notesEditText.text.toString(),
                uriString
            )
            val database = AppDatabase.getDatabase(this@CreatePetPage)
            database.petDao().insertAll(pet.generatePetEntity())
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) { return }

        data.getData()?.let { avatarUriOnResult ->
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                contentResolver.takePersistableUriPermission(
                    avatarUriOnResult,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                imageLoader.handleRequest(requestCode, resultCode, avatarUriOnResult)?.let {
                    uploadPetAvatarButton.setImageBitmap(it)
                }
            }
        }
    }
}