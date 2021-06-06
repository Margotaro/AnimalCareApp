package com.example.petproject2.database

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
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
        fun getPetUriStringFromDrawable(imageName: String, context: Context) : String {
            val resources = context.resources

            val packageName = context.packageName
            val imageId = context.getResources().getIdentifier(imageName, "drawable", packageName)
            return Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(imageId))
                .appendPath(resources.getResourceTypeName(imageId))
                .appendPath(resources.getResourceEntryName(imageId))
                .build().toString()
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
                            getPetUriStringFromDrawable("bear", context)
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
                            getPetUriStringFromDrawable("puma", context)
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
                            getPetUriStringFromDrawable("deers", context)
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
                            getPetUriStringFromDrawable("lenivec", context)
                    )
                )

                database.petDao().insertAll(*pets)
            }


            if (currentNotificationCount == 0) {
                val notifications = arrayOf(
                        NotificationEntity(0, 1, "Купання", 1586822400L * 1000L, false),
                        NotificationEntity(0, 1, "Годування сьомгою", 1586822400L * 1000L, false),
                        NotificationEntity(0, 1, "Тренування", 1586822400L * 1000L, false),
                        NotificationEntity(0, 1, "Ветеринарний візит", 1586822400L * 1000L, false),
                        NotificationEntity(0, 2, "Перев'язка", 1586822400L * 1000L, false)
                )

                database.notificationDao().insertAll(*notifications)
            }

            if(currentVetNoteCount == 0) {
                val vetNotes = arrayOf(
                        VetNoteEntity(0, 1, "Вага: 425 кг.\n" +
                                "Зріст: 215 см.\n" +
                                "Температура: 38.7 (нормотермія).\n" +
                                "ЧСС 130 ударів / хвилину (норма).\n" +
                                "Висновок: патологій при огляді не виявлено, здоровий.", "І.В.Пападімі", "Медикаментів не потребує", 1610268825L * 1000L),
                        VetNoteEntity(0, 1, "Вага: 422 кг.\n" +
                                "Зріст: 215 см.\n" +
                                "Температура: 38.6 (нормотермії).\n" +
                                "ЧСС: 132 ударів / хвилину (норма).\n" +
                                "Висновок: Патологій при огляді не виявлено, здоровий.", "І.В.Пападімі", "Медикаментів не потребує", 1613120025L * 1000L),
                        VetNoteEntity(0, 1, "Вага: 420 кг.\n" +
                                "Зріст: 215 см.\n" +
                                "Температура (при ректальному вимірі): 39.5 ° С (гіпертермія).\n" +
                                "ЧСС 156 ударів в хвилину (тахікардія).\n" +
                                "Діагноз встановлений на підставі клінічної картини, а також лабораторних та інструментальних критеріїв: виявлення еозинофілії, наростання титру специфічних антитіл в реакції РНГА в три рази, а також на підставі біопсії м'язів з виявленням личинки.", "І.В.Пападімі", "антибіотикотерапія - мебендазол в дозуванні 4.0 г всередину", 1615798425L * 1000L),
                        VetNoteEntity(0, 1, "Вага: 424 кг.\n" +
                                "Зріст: 215 см.\n" +
                                "Температура: 38.9 (нормотермії).\n" +
                                "ЧСС: 138 ударів / хвилину (норма).\n" +
                                "Діагноз: Тріхіннеллёз, гострий перебіг. Стан нормалізувався.", "К.В.Романенко", "антибіотикотерапія - мебендазол в дозуванні 4.0 г всередину", 1615971225L * 1000L),
                        VetNoteEntity(0, 1, "Вага: 424 кг.\n" +
                                "Зріст: 215 см.\n" +
                                "Температура: 38.4 (нормотермії).\n" +
                                "ЧСС: 136 ударів / хвилину (норма).\n" +
                                "Висновок: Патологій при огляді не виявлено, здоровий.", "І.В.Пападімі", "Медикаментів не потребує", 1616662425L * 1000L),
                        VetNoteEntity(0, 1, "Вага: 427 кг.\n" +
                                "Зріст: 216 см.\n" +
                                "Температура: 39.7 (гіпертермія).\n" +
                                "ЧСС: 137 ударів / хвилину (норма).\n" +
                                "Діагноз встановлений на підставі клінічної картини: гіпертермія, гіперемія кон'юнктиви ока, сльозоточивість.\n" +
                                "Діагноз: Блефарит лівого ока, вогнищевий.", "І.В.Пападімі", "гігієна вік - обробка вік розчином хлоргексидину.\n" +
                                "Мазь тетрациклінова 1% наносити на повреждненний очей 3 рази в день.", 1618908825L * 1000L),
                        VetNoteEntity(0, 1, "Вага: 427 кг.\n" +
                                "Зріст: 216 см.\n" +
                                "Температура: 39.7 (гіпертермія).\n" +
                                "ЧСС: 131 ударів / хвилину (норма).\n" +
                                "Діагноз встановлений на підставі клінічної картини: гіпертермія, гіперемія кон'юнктиви ока, сльозоточивість обох очей.\n" +
                                "Діагноз: Блефарит, двосторонній." +
                                "Лікування: Лікування не ефективно, необхідно змінити схему.", "І.В.Пападімі", "Гігієна вік - обробка вік розчином хлоргексидину.\n" +
                                "Мазь левомецетіновая 1,5% наносити на очі 3 рази на день.\n" +
                                "Мазь преднізалоновая 0,5% наносити на очі 2 рази на день", 1619340825L * 1000L),
                        VetNoteEntity(0, 1, "Вага: 425 кг.\n" +
                                "Зріст: 215 см.\n" +
                                "Температура: 38.1 (нормотермії).\n" +
                                "ЧСС: 133 ударів / хвилину (норма).\n" +
                                "Діагноз: Блефарит, двосторонній. Стан нормалізувався.", "І.В.Пападімі", "гігієна вік - обробка вік розчином хлоргексидину.\n" +
                                "Мазь левомецетіновая 1,5% наносити на очі 3 рази на день.\n" +
                                "Мазь преднізалоновая 0,5% наносити на очі 2 рази на день", 1619427225L * 1000L),
                        VetNoteEntity(0, 1, "Вага: 425 кг.\n" +
                                "Зріст: 215 см.\n" +
                                "Температура: 38.4 (нормотермії).\n" +
                                "ЧСС: 136 ударів / хвилину (норма).\n" +
                                "Висновок: Патологій при огляді не виявлено, здоровий.", "І.В.Пападімі", "Медикаментів не потребує", 1620032025L * 1000L),
                        VetNoteEntity(0, 1, "Вага: 426 кг.\n" +
                                "Зріст: 215 см.\n" +
                                "Температура: 38.4 (нормотермії).\n" +
                                "ЧСС: 136 ударів / хвилину (норма).\n" +
                                "Висновок: Патологій при огляді не виявлено, здоровий.", "І.В.Пападімі", "Медикаментів не потребує", 1622192025L * 1000L)
                )

                database.vetNoteDao().insertAll(*vetNotes)
            }

            if(currentIlnessTypeCount == 0) {
                val illnesses = arrayOf(
                    IllnessTypeEntity(0, 3, "загострення"),
                    IllnessTypeEntity(0, 4, "загострення"),
                    IllnessTypeEntity(0, 6, "загострення"),
                    IllnessTypeEntity(0, 7, "загострення"),
                    IllnessTypeEntity(0, 3, "тріхенельоз"),
                    IllnessTypeEntity(0, 6, "блефарит"),
                    IllnessTypeEntity(0, 3, "гіпертермія"),
                    IllnessTypeEntity(0, 3, "тахікардія"),
                    IllnessTypeEntity(0, 4, "тріхенельоз"),
                    IllnessTypeEntity(0, 6, "гіпертермія"),
                    IllnessTypeEntity(0, 7, "гіпертермія"),
                    IllnessTypeEntity(0, 7, "блефарит"),
                    IllnessTypeEntity(0, 8, "блефарит")
                )

                database.ilnessDao().insertAll(*illnesses)
            }
        }
    }
}