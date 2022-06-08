package ru.mirea.armforpolyclinics.presentation.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mirea.armforpolyclinics.data.HospitalRepositoryImpl
import ru.mirea.armforpolyclinics.domain.entity.Appointment
import ru.mirea.armforpolyclinics.domain.entity.Doctor
import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.usecase.AddNewAppointmentUseCase
import ru.mirea.armforpolyclinics.domain.usecase.GetAllDoctorsUseCase
import java.util.*
import kotlin.random.Random

class AddAppointmentFragmentViewModel(application: Application):AndroidViewModel(application) {





    private val _isAdded:MutableLiveData<String> = MutableLiveData()
    val isAdded:LiveData<String>
        get() = _isAdded

    private val _errorInputInternetConnection = MutableLiveData<Boolean>()
    val errorInputInternetConnection: LiveData<Boolean>
        get() = _errorInputInternetConnection
    private val addNewAppointmentUseCase =  AddNewAppointmentUseCase(HospitalRepositoryImpl())
    private val getAllDoctorsUseCase = GetAllDoctorsUseCase(HospitalRepositoryImpl())


    fun addAppointment(patient:Patient, doctor: Doctor,date: String){
        viewModelScope.launch {
            if(isInternetAvailable()){
                val a = Appointment(Random.nextLong(),date,doctor.name,doctor.profession,doctor.cabinet, patient.id)
                _errorInputInternetConnection.value = false
                    val flag:String = addNewAppointmentUseCase.invoke(a)
                        _isAdded.value = flag
                }
                _errorInputInternetConnection.value = true
            }

        }
    private val _doctorsList: MutableLiveData<Pair<String,List<Doctor>?>> = MutableLiveData()
    val doctorsList: LiveData<Pair<String,List<Doctor>?>>
        get()=_doctorsList


    fun getAllDoctors(){
        viewModelScope.launch {
            if(isInternetAvailable()){
                _errorInputInternetConnection.value = false
                _doctorsList.value= getAllDoctorsUseCase.invoke()
            }else{
                _errorInputInternetConnection.value = true
            }

        }

    }

    private fun isInternetAvailable(): Boolean {
        val cm = ContextCompat.getSystemService<ConnectivityManager>(getApplication<Application>().applicationContext,
            ConnectivityManager::class.java
        )
        return cm!!.activeNetworkInfo != null && cm!!.activeNetworkInfo!!.isConnected
    }
}