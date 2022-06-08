package ru.mirea.armforpolyclinics.domain.usecase

import ru.mirea.armforpolyclinics.domain.entity.Employee
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository

class RegisterUseCase (
    private val repository: HospitalRepository
) {
    suspend operator fun invoke(employee: Employee):String{
        return repository.register(employee)
    }

    companion object{
        const val REGISTER_SUCCESS = "Вы успешно зарегистрировались"
        const val REGISTER_IS_NOT_SUCCESS = "Почта уже привязана"
        const val REGISTER_IS_NOT_SUCCESS_API = "Что-то пошло не так на стороне сервера"
    }

}