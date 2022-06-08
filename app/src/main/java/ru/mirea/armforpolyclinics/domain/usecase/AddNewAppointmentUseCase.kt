package ru.mirea.armforpolyclinics.domain.usecase

import ru.mirea.armforpolyclinics.domain.entity.Appointment
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository

class AddNewAppointmentUseCase (
    private val repository: HospitalRepository
) {
    suspend operator fun invoke(appointment: Appointment): String =
        repository.addNewAppointment(appointment)
    companion object{
        const val ADD_APPOINTMENT_SUCCESS = "Запись успешно добавлена"
        const val ADD_APPOINTMENT_IS_NOT_SUCCESS = "Запись не добавлена"
        const val ADD_APPOINTMENT_IS_NOT_SUCCESS_API = "Что-то пошло не так на стороне сервера"
    }
}