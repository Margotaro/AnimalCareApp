package com.example.petproject2.database

import androidx.room.*

@Dao
interface IllnessDao {

    @Insert
    fun insertAll(vararg users: IllnessTypeEntity)

    @Delete
    fun delete(user: IllnessTypeEntity)

    @Update
    fun update(user: IllnessTypeEntity)

    @Query("SELECT count(*) from illnesstypeentity")
    fun count() : Int

    @Query("SELECT DISTINCT illnessName FROM petentity JOIN vetnoteentity ON petentity.id = vetnoteentity.petId  AND petentity.id = :petId JOIN illnesstypeentity ON vetnoteentity.id = illnesstypeentity.vetNoteId")
    fun getAllDistinct(petId: Int) : List<String>
}