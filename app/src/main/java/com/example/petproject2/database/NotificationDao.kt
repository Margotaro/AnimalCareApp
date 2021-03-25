package com.example.petproject2.database

import androidx.room.*


class PetNotifications {
    @Embedded
    var pet: PetEntity? = null

    @Relation(parentColumn = "id", entityColumn = "petId")
    var bar: List<NotificationEntity>? = null
}


@Dao
interface NotificationDao {

    @Insert
    fun insertAll(vararg users: NotificationEntity)

    @Delete
    fun delete(user: NotificationEntity)

    @Query("SELECT count(*) from notificationentity")
    fun count() : Int

    @Query("SELECT petentity.*, notificationentity.* FROM notificationentity INNER JOIN petentity ON petentity.id = notificationentity.petId WHERE petentity.id = :pet")
    fun findPetNotifications(pet: Int): PetNotifications?

    @Query("SELECT * FROM notificationentity")
    fun getAll(): List<NotificationEntity>
}