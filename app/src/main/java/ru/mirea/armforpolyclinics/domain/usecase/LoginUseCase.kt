package ru.mirea.armforpolyclinics.domain.usecase

import ru.mirea.armforpolyclinics.data.pojo.JwtResponse
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository

class LoginUseCase (
    private val repository: HospitalRepository
) {
    suspend operator fun invoke(email:String, password:String):String{
        return repository.login(email,password)
    }

    companion object{
        const val LOGIN_SUCCESS = "Вы успешно авторизировались"
        const val LOGIN_IS_NOT_SUCCESS = "Неправильная почта или пароль"
        const val LOGIN_IS_NOT_SUCCESS_API = "Что-то пошло не так на стороне сервера"
    }
}