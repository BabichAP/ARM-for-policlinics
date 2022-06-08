package ru.mirea.armforpolyclinics.presentation.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mirea.armforpolyclinics.data.HospitalRepositoryImpl
import ru.mirea.armforpolyclinics.domain.usecase.LoginUseCase


class LoginViewModel(application: Application) : AndroidViewModel(application) {



    private val loginUseCase = LoginUseCase(HospitalRepositoryImpl())

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _errorInputInternetConnection = MutableLiveData<Boolean>()
    val errorInputInternetConnection: LiveData<Boolean>
        get() = _errorInputInternetConnection


    private val _isLoggedIn:MutableLiveData<String> = MutableLiveData()
    val isLoggedIn:LiveData<String>
        get() = _isLoggedIn

     fun login(password: String, email:String){
         var isValid = validateInput(email, password)
       viewModelScope.launch {
           if(isInternetAvailable()){
               _errorInputInternetConnection.value = false
            if(isValid) {
                val flag:String = loginUseCase(email = email, password = password)
                    _isLoggedIn.value = flag
            }
           }else{
               _errorInputInternetConnection.value = true
           }

         }
     }

    fun validateInput(email: String, password: String):Boolean{
        var result = true
        if(email.isBlank()
            ||!"""^[A-Za-z0-9._%+-]+@[a-zA-Z]+\.[a-zA-Z]{2,4}$""".toRegex().matches(email)){
            _errorInputEmail.value = true
            result = false
        }
        if(password.isBlank()
            || password.length < 8
            || "[a-z]".toRegex() !in password
            || "[A-Z]".toRegex() !in password
            || "[0-9]".toRegex() !in password
        ){
            _errorInputPassword.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }

    fun isInternetAvailable(): Boolean {
        val cm =getSystemService(getApplication<Application>().applicationContext,
            ConnectivityManager::class.java)

        return cm!!.activeNetworkInfo != null && cm!!.activeNetworkInfo!!.isConnected
    }
}