package com.example.petproject2

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
            R.drawable.avatar
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
                    R.drawable.bear
                ),
                Pet(
                    "Bagheera",
                    "Puma",
                    "N/A",
                    "Yes",
                    "2333457",
                    "Rozumovska, 10/12",
                    "Do not touch",
                    R.drawable.puma
                ),
                Pet(
                    "Mime",
                    "Deer",
                    "Chital",
                    "No",
                    "2333457",
                    "Rozumovska, 10/12",
                    "Has horns",
                    R.drawable.deers
                ),
                Pet(
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
        )
        accounts.add(acc1)
        registerData.add(Triple<String, String, Account>("login", "password", accounts[0]))
        notifications = mutableListOf(
                Alarm("alarm 1 test text", 1586822400L * 1000L, 1, true, true),
                Alarm("alarm 2 test text", 1586822400L * 1000L, 1, false, true),
                Alarm("alarm 3 test text", 1586822400L * 1000L, 1, true, false))
    }
    override fun register(
        login: String,
        password: String,
        name: String,
        email: String,
        telephone_number: String
    ): Boolean {

        var newAccount = Account(name, email, telephone_number, 0)
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

    override fun updateAccount(name: String, email: String, telephone_number: String, avatar: Int):Boolean {
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
        avatar: Int
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
        avatar: Int
    ): Boolean  {
        loggedInInstance?.findPet(name)?.let {
            it.updateObject(name, species, breed, neuter, chip, vet_address, notes, avatar)
            return true
        }
        return true
    }

    override fun getPetTileCollection(): MutableList<Pair<String, Int>> {
        return loggedInInstance?.pets?.map { Pair(it.name, it.image) }!!.toMutableList()
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

