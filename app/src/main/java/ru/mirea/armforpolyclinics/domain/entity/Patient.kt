package ru.mirea.armforpolyclinics.domain.entity

import com.example.hospital.model.BloodTypes
import java.io.Serializable
import java.util.*

data class Patient(
    val id: Long,
    val name: String,
    val surname: String,
    val patronymic: String,
    val gender: Genders,
    val address: String,
    val phoneNumber: String,
    val snils:String,
    val policyNumber: String,
    val bloodType:BloodTypes,
    val age: Int
) : Serializable