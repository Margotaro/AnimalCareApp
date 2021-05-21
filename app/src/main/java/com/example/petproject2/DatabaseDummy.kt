package com.example.petproject2

import android.graphics.drawable.Drawable

class DatabaseDummy: DataBase {
    private var accounts: MutableList<Account> = mutableListOf()
    private var registerData: MutableList<Triple<String, String, Account>> = mutableListOf()
    private var loggedInInstance: Account? = null
    private var notifications: MutableList<Alarm> = mutableListOf()
    init {
        var acc1: Account = Account(
            "Margarita",
            "melifarofrei@gmail.com",
            "+380950416302",
            "avatar"
        )
        acc1.pets.addAll(
            listOf(
                Pet(
                    "Barnie",
                    "Bear",
                    "White Bear",
                    "No",
                    "2333456",
                    "Rozumovska, 10/12",
                    "Is quite friendly pal, at least when full",
                    "bear"
                ),
                Pet(
                    "Bagheera",
                    "Puma",
                    "N/A",
                    "Yes",
                    "2333457",
                    "Rozumovska, 10/12",
                    "Do not touch",
                    "puma"
                ),
                Pet(
                    "Mime",
                    "Deer",
                    "Chital",
                    "No",
                    "2333457",
                    "Rozumovska, 10/12",
                    "Has horns",
                    "deers"
                ),
                Pet(
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
        )
        var r: Drawable? = null

        accounts.add(acc1)
        registerData.add(Triple<String, String, Account>("login", "password", accounts[0]))
        notifications = mutableListOf(
                Alarm(1, "alarm 1 test text", 1586822400L * 1000L, 1, true, true),
                Alarm(2, "alarm 2 test text", 1586822400L * 1000L, 1, false, true),
                Alarm(3, "alarm 3 test text", 1586822400L * 1000L, 1, true, false))
    }
    override fun register(
        login: String,
        password: String,
        name: String,
        email: String,
        telephone_number: String
    ): Boolean {

        var newAccount = Account(name, email, telephone_number, "avatar")
        for(data in registerData) {
            if(data.first == login && data.second == password) {
                return false
            }
        }
        registerData.add(Triple<String, String, Account>(login, password, newAccount))
        accounts.add(newAccount)//to do: make sure all other lists also change
        return true
    }

    override fun signIn(login: String, password: String): Boolean {
        for(data in registerData) {
            if(data.first == login && data.second == password) {
                loggedInInstance = data.third
            }
            return true
        }
        return false
    }

    override fun updateAccount(name: String, email: String, telephone_number: String, avatar: String):Boolean {
        loggedInInstance?.let{
            it.updateObject(name, email, telephone_number)
            return true
        }
        return false
    }

    override fun createAnimal(
        name: String,
        species: String,
        breed: String,
        neuter: String,
        chip: String,
        vet_address: String,
        notes: String,
        avatar: String
    ):Boolean  {
        loggedInInstance?.pets?.let{
            it.add(Pet(name, species, breed, neuter, chip, vet_address, notes, avatar))//to do: make sure all other lists also change
            return true
        }
        return false
    }

    override fun updateAnimal(
        name: String,
        species: String,
        breed: String,
        neuter: String,
        chip: String,
        vet_address: String,
        notes: String,
        avatar: String
    ): Boolean  {
        loggedInInstance?.findPet(name)?.let {
            it.updateObject(name, species, breed, neuter, chip, vet_address, notes, avatar)
            return true
        }
        return true
    }

    override fun getPetTileCollection(): MutableList<Pair<String, String?>> {
        loggedInInstance?.let {
            return it.pets.map { Pair(it.name, it.image) }.toMutableList()
        }
        return mutableListOf()
    }
    override fun getAccount(name: String): Account {
        return searchAccountsFor(name)
    }

    override fun getAnimal(ownerName: String, petName: String): Pet {
        return searchAccountsFor(ownerName).findPet(petName)
    }

    override fun getAlarmList(ownerName: String, petName: String): MutableList<Alarm> {
        return notifications
    }
    private fun searchAccountsFor(id: String): Account {
        return accounts.first { it.name == id }
    }

}

