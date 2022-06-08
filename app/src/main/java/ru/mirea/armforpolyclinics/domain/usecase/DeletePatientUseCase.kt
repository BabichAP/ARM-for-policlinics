package ru.mirea.armforpolyclinics.domain.usecase

import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository

class DeletePatientUseCase(private val repository: HospitalRepository
) {
    suspend operator fun invoke(id:Long):Pair<String, List<Patient>?> = repository.deletePatient(id)
}