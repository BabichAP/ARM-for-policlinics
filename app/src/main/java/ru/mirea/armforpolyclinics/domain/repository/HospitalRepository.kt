package ru.mirea.armforpolyclinics.domain.repository

import ru.mirea.armforpolyclinics.domain.entity.Appointment
import ru.mirea.armforpolyclinics.domain.entity.Doctor
import ru.mirea.armforpolyclinics.domain.entity.Employee
import ru.mirea.armforpolyclinics.domain.entity.Patient

interface HospitalRepository{

    suspend fun addNewAppointment(appointment: Appointment): String
    suspend fun addNewPatient(patient: Patient):String
    suspend fun getAllDoctors():Pair<String,List<Doctor>?>
    suspend fun getAllPatients(): Pair<String,List<Patient>?>
    suspend fun getAllAppointment(id:Long): Pair<String,List<Appointment>?>
    suspend fun deletePatient(id:Long):Pair<String, List<Patient>?>
    suspend fun deleteAppointment(id_deleted: Long, id_patient:Long):Pair<String, List<Appointment>?>
    suspend fun login(email: String, password: String): String
    suspend fun register(employee: Employee): String


}