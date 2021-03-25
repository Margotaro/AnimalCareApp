package com.example.petproject2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PetDao {
    @Query("SELECT * FROM petentity")
    fun getAll(): List<PetEntity>

    @Query("SELECT * FROM petentity WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<PetEntity>

    @Query("SELECT * FROM petentity WHERE name LIKE :first LIMIT 1")
    fun findByName(first: String): PetEntity

    @Insert
    fun insertAll(vararg users: PetEntity)

    @Delete
    fun delete(user: PetEntity)

    @Query("SELECT count(*) from petentity")
    fun count() : Int
}