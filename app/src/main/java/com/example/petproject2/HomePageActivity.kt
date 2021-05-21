package com.example.petproject2

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import com.example.petproject2.database.AppDatabase
import com.example.petproject2.database.PetEntity
import kotlinx.android.synthetic.main.activity_home_page.*

private const val CREATE_RESULT_OK = 1

class HomePageActivity : Activity() {

    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        imageView.setImageResource(R.drawable.avatar)

        loadFromDatabase()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CREATE_RESULT_OK) {
            database.let {
                loadFromDatabase()
            }
        }
    }

    fun loadFromDatabase() {
        database = AppDatabase.getDatabase(this)

        val petList = database.petDao().getAll()

        val adapter = ImageListAdapter(this, R.layout.owner_page_pet_icon, petList)
        petTableLayout.adapter = adapter

        petTableLayout.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->

            if(position >= petList.size)
            {
                startActivityForResult(Intent(this, CreatePetPage::class.java), CREATE_RESULT_OK)
                return@OnItemClickListener
            }

            val intent = Intent(this, PetPageActivity::class.java)


            val imageId = this.getResources().getIdentifier("pet_placeholder1", "drawable", packageName)
            val placeholderURI = Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(imageId))
                .appendPath(resources.getResourceTypeName(imageId))
                .appendPath(resources.getResourceEntryName(imageId))
                .build().toString()
            val petName = petList.getOrNull(position)?.name ?: return@OnItemClickListener
            val petImageResource = petList.getOrNull(position)?.image ?: placeholderURI
            val petId = petList.getOrNull(position)?.id ?: return@OnItemClickListener

            intent.putExtra("Name", petName)
            intent.putExtra("Avatar", petImageResource)
            intent.putExtra("PetId", petId)

            startActivity(intent)
        }
    }
}