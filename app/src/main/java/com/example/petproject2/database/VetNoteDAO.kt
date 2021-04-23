package com.example.petproject2.database

import androidx.room.*

@Dao
interface VetNoteDao {

    @Insert
    fun insertAll(vararg users: VetNoteEntity)

    @Delete
    fun delete(user: VetNoteEntity)

    @Update
    fun update(user: VetNoteEntity)

    @Query("SELECT count(*) from vetnoteentity")
    fun count() : Int

    @Query("SELECT * FROM vetnoteentity INNER JOIN petentity ON petentity.id = vetnoteentity.petId WHERE petentity.id = :pet")
    fun findPetMedNotes(pet: Int): List<VetNoteEntity>

    @Query("SELECT * FROM vetnoteentity INNER JOIN petentity ON petentity.id = vetnoteentity.petId INNER JOIN illnesstypeentity ON illnesstypeentity.vetNoteId = vetnoteentity.id WHERE petentity.id = :pet AND illnesstypeentity.illnessName = :illness")
    fun findPetMedNotesByIllness(pet: Int, illness: String): List<VetNoteEntity>
}