package com.example.petproject2

interface DataBase {
    fun register(login: String, password: String, name: String, email: String, telephone_number: String): Boolean
    fun signIn(login: String, password: String): Boolean
    fun updateAccount(name: String, email: String, telephone_number: String, avatar: String): Boolean
    fun createAnimal(name: String,
                     species: String,
                     breed: String,
                     neuter: String,
                     chip: String,
                     vet_address: String,
                     notes: String,
                     avatar: String): Boolean
    fun updateAnimal(name: String,
                     species: String,
                     breed: String,
                     neuter: String,
                     chip: String,
                     vet_address: String,
                     notes: String,
                     avatar: String): Boolean
    fun getAccount(name: String): Account
    fun getAnimal(ownerName: String, petName: String): Pet
    fun getPetTileCollection(): MutableList<Pair<String, String>>?
    fun getAlarmList(ownerName: String, petName: String): MutableList<Alarm>
}
