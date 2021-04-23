package com.example.petproject2.database

import android.content.Context
import android.text.format.DateFormat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.petproject2.R
import java.util.*

@Database(entities = arrayOf(
        PetEntity::class,
        NotificationEntity::class,
        IllnessTypeEntity::class,
        VetNoteEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun notificationDao(): NotificationDao
    abstract fun ilnessDao(): IllnessDao
    abstract fun vetNoteDao(): VetNoteDao


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
            val currentNotificationCount = database.notificationDao().count()
            val currentIlnessTypeCount = database.ilnessDao().count()
            val currentVetNoteCount = database.vetNoteDao().count()

            if (currentPetCount == 0) {
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
                            "bear"
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
                            "puma"
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
                            "deers"
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
                            "lenivec"
                    )
                )

                database.petDao().insertAll(*pets)
            }


            if (currentNotificationCount == 0) {
                val notifications = arrayOf(
                        NotificationEntity(0, 1, "Washing", 1586822400L * 1000L, false),
                        NotificationEntity(0, 1, "Eaing", 1586822400L * 1000L, false),
                        NotificationEntity(0, 1, "Walking", 1586822400L * 1000L, false),
                        NotificationEntity(0, 1, "Vet visit", 1586822400L * 1000L, false),
                        NotificationEntity(0, 2, "Washing", 1586822400L * 1000L, false)
                )

                database.notificationDao().insertAll(*notifications)
            }

            if(currentVetNoteCount == 0) {
                val vetNotes = arrayOf(
                        VetNoteEntity(0, 1, "Note 1", "Doctor 1", "Med 1, med 2, med 3", 1586822400L * 1000L),
                        VetNoteEntity(0, 2, "Note 2", "Doctor 2", "Med 1, med 2, med 3", 1586822400L * 1000L),
                        VetNoteEntity(0, 2, "Note 3", "Doctor 1", "Med 1, med 2, med 3", 1586822400L * 1000L),
                        VetNoteEntity(0, 3, "Note 4", "Doctor 4", "Med 1, med 2, med 3", 1586822400L * 1000L),
                        VetNoteEntity(0, 4, "Note 5", "Doctor 1", "Med 1, med 2, med 3", 1586822400L * 1000L)
                )

                database.vetNoteDao().insertAll(*vetNotes)
            }

            if(currentIlnessTypeCount == 0) {
                val illnesses = arrayOf(
                        IllnessTypeEntity(0, 1, "tuberculosis"),
                        IllnessTypeEntity(0, 1, "scoliosis"),
                        IllnessTypeEntity(0, 2, "cavities"),
                        IllnessTypeEntity(0, 3, "cavities"),
                        IllnessTypeEntity(0, 3, "helminthiasis"),
                        IllnessTypeEntity(0, 4, "cavities"),
                        IllnessTypeEntity(0, 4, "flea")
                )

                database.ilnessDao().insertAll(*illnesses)
            }
        }
    }
}