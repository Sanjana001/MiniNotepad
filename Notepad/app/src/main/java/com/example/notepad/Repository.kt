package com.example.notepad


interface Repository {
    companion object{
        var isEdited:Boolean? = false
        var arrayList = mutableListOf<Model>()
        var backup = mutableListOf<String>()
    }
}