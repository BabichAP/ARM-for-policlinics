package ru.mirea.armforpolyclinics.domain.entity

import ru.mirea.armforpolyclinics.data.pojo.SignUpRequest
import java.util.*

data class Employee(
    val id: Long,
    val name: String,
    val surname: String,
    val patronymic: String,
    val phoneNumber: String,
    val email: String,
    val password:String
)


fun Employee.build(employee: Employee): SignUpRequest {
    return SignUpRequest(
        employee.name,
        employee.surname,
        employee.patronymic,
        employee.phoneNumber,
        employee.email,
        employee.password
    )
}