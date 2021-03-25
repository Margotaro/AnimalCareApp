package com.example.petproject2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.petproject2.R

@Database(entities = arrayOf(PetEntity::class, NotificationEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun notificationDao(): NotificationDao



    companion object {

        private var database : AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            val database = database

            if (database != null) {
                return database
            }

            AppDatabase.database = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "database-name"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

            populateDatabaseIfNeeded(context)

            return Companion.database!!
        }

        fun populateDatabaseIfNeeded(context: Context) {
            val database = getDatabase(context)
            val currentPetCount = database.petDao().count()

            if (currentPetCount != 0) {
                return
            }

            val pets = arrayOf(
                PetEntity(
                    0,
                    "Barnie",
                    "Bear",
                    "White Bear",
                    "No",
                    "2333456",
                    "Rozumovska, 10/12",
                    "Is quite friendly pal, at least when full",
                    R.drawable.bear
                ),
                PetEntity(
                    0,
                    "Bagheera",
                    "Puma",
                    "N/A",
                    "Yes",
                    "2333457",
                    "Rozumovska, 10/12",
                    "Do not touch",
                    R.drawable.puma
                ),
                PetEntity(
                    0,
                    "Mime",
                    "Deer",
                    "Chital",
                    "No",
                    "2333457",
                    "Rozumovska, 10/12",
                    "Has horns",
                    R.drawable.deers
                ),
                PetEntity(
                    0,
                    "Flash Slothmore",
                    "Sloth",
                    "Brown-throated sloth",
                    "No",
                    "2333457",
                    "Rozumovska, 10/12",
                    "Is slow",
                    R.drawable.lenivec
                )
            )

            val notificationEntity = NotificationEntity(0, 1, "notification")

            database.petDao().insertAll(*pets)

            database.notificationDao().insertAll(notificationEntity)
        }
    }
}