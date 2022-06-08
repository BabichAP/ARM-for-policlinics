package ru.mirea.armforpolyclinics.domain.usecase

import ru.mirea.armforpolyclinics.domain.entity.Appointment
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository

class GetAllAppointmentForUserUseCase (private val repository: HospitalRepository
) {
    suspend operator fun invoke(id:Long):Pair<String,List<Appointment>?> {
        return repository.getAllAppointment(id)
    }
    companion object{
        const val DOWNLOAD_SUCCESS = "Список записей успешно загружен"
        const val DOWNLOAD_IS_NOT_SUCCESS = "Список записей не загружен"
        const val DOWNLOAD_IS_NOT_SUCCESS_API = "Что-то пошло не так на стороне сервера"
    }
}