package ru.mirea.armforpolyclinics.domain.entity

import java.util.*


data class Appointment(
    val id: Long,
    val dateOfAppointment: String,
    val doctorName:String,
    val profession: Profession,
    val cabinet: String,
    val patientId:Long
)
