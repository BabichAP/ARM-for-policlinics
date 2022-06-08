package ru.mirea.armforpolyclinics.domain.entity

data class Doctor(
    var id: Long = 0,
    var name:String,
    var profession:Profession,
    var cabinet:String
)
