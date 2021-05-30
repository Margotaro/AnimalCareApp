package com.example.petproject2.database


class Document(name: String, type: String, link: String) {
    var name: String
    var type: String
    var link: String
    init {
        this.name = name
        this.link = link
        this.type = type
    }
}