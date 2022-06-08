package ru.mirea.armforpolyclinics.domain.usecase

import ru.mirea.armforpolyclinics.domain.entity.Appointment
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository

class DeleteAppointmentUseCase (private val repository: HospitalRepository
) {
    suspend operator fun invoke(id_deleted:Long, id_patient:Long):Pair<String, List<Appointment>?> = repository.deleteAppointment(id_deleted,id_patient)
}