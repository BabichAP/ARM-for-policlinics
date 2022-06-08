package ru.mirea.armforpolyclinics.domain.usecase

import ru.mirea.armforpolyclinics.domain.entity.Doctor
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository

class GetAllDoctorsUseCase (private val repository: HospitalRepository
) {
    suspend operator fun invoke():Pair<String,List<Doctor>?> {
        return repository.getAllDoctors()
    }
    companion object{
        const val DOWNLOAD_SUCCESS = "Список докторов успешно загружен"
        const val DOWNLOAD_IS_NOT_SUCCESS = "Список докторов не загружен"
        const val DOWNLOAD_IS_NOT_SUCCESS_API = "Что-то пошло не так на стороне сервера"
    }
}