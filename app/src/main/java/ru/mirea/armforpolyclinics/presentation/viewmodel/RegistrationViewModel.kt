package ru.mirea.armforpolyclinics.presentation.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.mirea.armforpolyclinics.data.HospitalRepositoryImpl
import ru.mirea.armforpolyclinics.domain.entity.Employee
import ru.mirea.armforpolyclinics.domain.usecase.RegisterUseCase
import kotlin.random.Random

class RegistrationViewModel(application: Application):AndroidViewModel(application) {


    private val registerUseCase = RegisterUseCase(HospitalRepositoryImpl())

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputSurName = MutableLiveData<Boolean>()
    val errorInputSurName: LiveData<Boolean>
        get() = _errorInputSurName

    private val _errorInputPatronymic = MutableLiveData<Boolean>()
    val errorInputPatronymic: LiveData<Boolean>
        get() = _errorInputPatronymic


    private val _errorInputPhoneNumber = MutableLiveData<Boolean>()
    val errorInputPhoneNumber: LiveData<Boolean>
        get() = _errorInputPhoneNumber


    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _errorInputInternetConnection = MutableLiveData<Boolean>()
    val errorInputInternetConnection: LiveData<Boolean>
        get() = _errorInputInternetConnection

    private val _isRegister:MutableLiveData<String> = MutableLiveData()
    val isRegister:LiveData<String>
        get() = _isRegister

    fun register(name:String,sureName:String,patronymic:String,phoneNumber:String, email: String, password:String){
        var isValid = validateInput(name, sureName, patronymic,phoneNumber,email,password)
        viewModelScope.launch {
            if(isInternetAvailable()){
                _errorInputInternetConnection.value = false
                if(isValid) {
                    val flag:String? = registerUseCase(Employee(Random.nextLong(),name,sureName,patronymic,phoneNumber,email,password))
                    if(flag != null) {
                        _isRegister.value = flag
                    }
                }
            }else{
                _errorInputInternetConnection.value = true
            }

        }
    }


    fun validateInput(name:String,surName:String,patronymic:String,phoneNumber:String, email: String, password:String):Boolean{
        var result = true
        if(name.isBlank() || name.contains("""[0-9.-?+\\*!,@'"№\(\)\{\}\[\]&]""".toRegex())){
            _errorInputName.value = true
            result = false
        }
        if(surName.isBlank()|| surName.contains("""[0-9.-?+\\*!,@'"№\(\)\{\}\[\]&]""".toRegex())){
            _errorInputSurName.value = true
            result = false
        }
        if(patronymic.isBlank()|| patronymic.contains("""[0-9.-?+\\*!,@'"№\(\)\{\}\[\]&]""".toRegex())){
            _errorInputPatronymic.value = true
            result = false
        }
        if(phoneNumber.isBlank() || phoneNumber.trim().length != 10){
            _errorInputPhoneNumber.value = true
            result = false
        }
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
    fun resetErrorInputName() {
        _errorInputName.value = false
    }
    fun resetErrorInputSurname() {
        _errorInputSurName.value = false
    }
    fun resetErrorInputPatronymic() {
        _errorInputPatronymic.value = false
    }
    fun resetErrorInputPhoneNumber() {
        _errorInputPhoneNumber.value = false
    }


    private fun isInternetAvailable(): Boolean {
        val cm = ContextCompat.getSystemService<ConnectivityManager>(
            getApplication<Application>().applicationContext,
            ConnectivityManager::class.java
        )

        return cm!!.activeNetworkInfo != null && cm!!.activeNetworkInfo!!.isConnected
    }
}