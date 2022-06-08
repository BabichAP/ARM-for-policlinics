package ru.mirea.armforpolyclinics.domain.usecase

import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository

class GetAllPatientsUseCase(private val repository:HospitalRepository
) {
    suspend operator fun invoke():Pair<String,List<Patient>?> {
        return repository.getAllPatients()
    }
    companion object{
        const val DOWNLOAD_SUCCESS = "Список пациентов успешно загружен"
        const val DOWNLOAD_IS_NOT_SUCCESS = "Список пациентов не загружен"
        const val DOWNLOAD_IS_NOT_SUCCESS_API = "Что-то пошло не так на стороне сервера"
    }
}