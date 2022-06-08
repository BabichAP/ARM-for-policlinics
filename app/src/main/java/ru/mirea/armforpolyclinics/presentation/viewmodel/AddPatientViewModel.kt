package ru.mirea.armforpolyclinics.presentation.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hospital.model.BloodTypes
import kotlinx.coroutines.launch
import ru.mirea.armforpolyclinics.data.HospitalRepositoryImpl
import ru.mirea.armforpolyclinics.domain.entity.Employee
import ru.mirea.armforpolyclinics.domain.entity.Genders
import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.usecase.AddNewPatientUseCase
import ru.mirea.armforpolyclinics.domain.usecase.RegisterUseCase
import kotlin.random.Random

class AddPatientViewModel(application:Application):AndroidViewModel(application) {



    private val addNewPatientUseCase = AddNewPatientUseCase(HospitalRepositoryImpl())

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

    private val _errorInputAddress = MutableLiveData<Boolean>()
    val errorInputAddress: LiveData<Boolean>
        get() = _errorInputAddress

    private val _errorInputSnils = MutableLiveData<Boolean>()
    val errorInputSnils: LiveData<Boolean>
        get() = _errorInputSnils

    private val _errorInputPolicy = MutableLiveData<Boolean>()
    val errorInputPolicy: LiveData<Boolean>
        get() = _errorInputPolicy

    private val _errorInputAge = MutableLiveData<Boolean>()
    val errorInputAge: LiveData<Boolean>
        get() = _errorInputAge


    private val _errorInputInternetConnection = MutableLiveData<Boolean>()
    val errorInputInternetConnection: LiveData<Boolean>
        get() = _errorInputInternetConnection

    private val _isAdded: MutableLiveData<String> = MutableLiveData()
    val isAdded: LiveData<String>
        get() = _isAdded

    fun addNewPatient(name:String,surName:String,patronymic:String,gender: Genders,address:String,phoneNumber:String,snils:String,policy:String,bloodTypes: BloodTypes,age:Int){
        val isValid = validateInput(name, surName, patronymic,address,phoneNumber,snils, policy,  age)
        viewModelScope.launch {
            if(isInternetAvailable()){
                _errorInputInternetConnection.value = false
                if(isValid) {
                    val flag:String = addNewPatientUseCase(Patient(Random.nextLong(),name, surName, patronymic,gender,address,phoneNumber,snils,policy,bloodTypes,age))
                        _isAdded.value = flag
                }
            }else{
                _errorInputInternetConnection.value = true
            }

        }
    }


    fun validateInput(name:String,surName:String,patronymic:String,address:String,phoneNumber:String,snils:String,policy:String,age:Int):Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (surName.isBlank() ) {
            _errorInputSurName.value = true
            result = false
        }
        if (patronymic.isBlank() ) {
            _errorInputPatronymic.value = true
            result = false
        }
        if (phoneNumber.isBlank() || phoneNumber.trim().length != 10) {
            _errorInputPhoneNumber.value = true
            result = false
        }
        if(address.isBlank() || address.trim().isEmpty()){
            _errorInputAddress.value = true
            result = false
        }
        if(snils.trim().length != 11){
            _errorInputSnils.value = true
            result = false
        }
        if(policy.length != 16){
            _errorInputPolicy.value = true
            result = false
        }
        if(age < 0){
            _errorInputAge.value = true
            result = false
        }
        return result
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
    fun resetErrorInputAddress() {
        _errorInputAddress.value = false
    }
    fun resetErrorInputSnils() {
        _errorInputSnils.value = false
    }
    fun resetErrorInputPolicy() {
        _errorInputPolicy.value = false
    }

    fun resetErrorInputAge() {
        _errorInputAge.value = false
    }


    private fun isInternetAvailable(): Boolean {
        val cm = ContextCompat.getSystemService<ConnectivityManager>(
            getApplication<Application>().applicationContext,
            ConnectivityManager::class.java
        )

        return cm!!.activeNetworkInfo != null && cm!!.activeNetworkInfo!!.isConnected
    }
}