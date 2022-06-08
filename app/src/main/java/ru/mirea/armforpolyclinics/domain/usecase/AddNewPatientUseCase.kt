package ru.mirea.armforpolyclinics.domain.usecase

import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository

class AddNewPatientUseCase (
    private val repository: HospitalRepository
) {
    suspend operator fun invoke(patient: Patient):String{
        return repository.addNewPatient(patient)
    }
    companion object{
        const val ADD_PATIENT_SUCCESS = "Пациент успешно добавлен"
        const val ADD_PATIENT_IS_NOT_SUCCESS = "Пациент не добавлен"
        const val ADD_PATIENT_IS_NOT_SUCCESS_API = "Что-то пошло не так на стороне сервера"
    }
}