package ru.mirea.armforpolyclinics.data.pojo

import ru.mirea.armforpolyclinics.domain.entity.Employee

data class SignUpRequest(var name: String,
                         var surname: String,
                         var patronymic: String,
                         var phoneNumber: String,
                         var email: String,
                          var password: String,
                          var authorities:Set<String> = setOf("user")
                          ){


}
