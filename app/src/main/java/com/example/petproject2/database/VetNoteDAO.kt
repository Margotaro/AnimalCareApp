package com.example.petproject2.database

import androidx.room.*

class CompleteVetNote {
    @Embedded
    var vetNote: VetNoteEntity? = null

    @Relation(parentColumn = "id", entityColumn = "vetNoteId")
    var bar: List<IllnessTypeEntity>? = null
}

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

    @Transaction
    @Query("SELECT * FROM vetnoteentity " +
            "WHERE vetnoteentity.petId = :pet")
    fun findPetMedNotes(pet: Int): List<CompleteVetNote>

    @Query("SELECT * FROM illnesstypeentity " +
            "INNER JOIN vetnoteentity ON " +
            "vetnoteentity.id = illnesstypeentity.vetNoteId " +
            "WHERE vetnoteentity.petId = :pet AND illnesstypeentity.illnessName = :illness")
    fun findPetMedNotesByIllness(pet: Int, illness: String): List<CompleteVetNote>
}