package com.example.petproject2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.example.petproject2.database.AppDatabase
import com.example.petproject2.database.PetEntity
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        imageView.setImageResource(R.drawable.avatar)

        loadFromDatabase()

//        val petTileCollection = Model.db.getPetTileCollection()
//        var adapter = ImageListAdapter(this, R.layout.owner_page_pet_icon, petTileCollection)
//        petGrid.adapter = adapter
//
//        petGrid.onItemClickListener = AdapterView.OnAlarmChangeListener { parent, v, position, id ->
//            var intent = Intent(this, PetPageActivity::class.java)
//            intent.putExtra("Name", petTileCollection?.elementAt(position)?.first)
//            intent.putExtra("Avatar", petTileCollection?.elementAt(position)?.second.toString())
//            if(position + 1 == petTileCollection?.size)
//            {
//                startActivity(Intent(this, CreatePetPage::class.java))
//            }
//            else {
//                startActivity(intent)
//            }
//        }
    }

    fun displayAnimals(animals: Array<PetEntity>) {

    }

    fun loadFromDatabase() {
        val database = AppDatabase.getDatabase(this)

        val petList = database.petDao().getAll()

        val adapter = ImageListAdapter(this, R.layout.owner_page_pet_icon, petList)
        petTableLayout.adapter = adapter

        petTableLayout.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->

            if(position >= petList.size)
            {
                startActivity(Intent(this, CreatePetPage::class.java))
                return@OnItemClickListener
            }

            val intent = Intent(this, PetPageActivity::class.java)


            val petName = petList.getOrNull(position)?.name ?: return@OnItemClickListener
            val petImageResource = petList.getOrNull(position)?.image ?: return@OnItemClickListener
            val petId = petList.getOrNull(position)?.id ?: return@OnItemClickListener

            intent.putExtra("Name", petName)
            intent.putExtra("Avatar", petImageResource)
            intent.putExtra("PetId", petId)

            startActivity(intent)
        }

        val pets = database.petDao().getAll()

        val allNotifications = database.notificationDao().getAll()
        val notifications = pets.firstOrNull()?.id?.let {
            database.notificationDao().findPetNotifications(it)
        }
        val r = 2 + 2
    }
}