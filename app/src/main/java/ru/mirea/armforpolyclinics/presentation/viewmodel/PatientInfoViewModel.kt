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
import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.usecase.DeleteAppointmentUseCase
import ru.mirea.armforpolyclinics.domain.usecase.DeletePatientUseCase
import ru.mirea.armforpolyclinics.domain.usecase.GetAllAppointmentForUserUseCase
import ru.mirea.armforpolyclinics.domain.usecase.GetAllPatientsUseCase

class PatientInfoViewModel(application: Application):AndroidViewModel(application) {
    private val _appointmentList: MutableLiveData<Pair<String, List<Appointment>?>> = MutableLiveData()
    val appointmentList: LiveData<Pair<String, List<Appointment>?>>
        get()=_appointmentList

    private val _errorInputInternetConnection = MutableLiveData<Boolean>()
    val errorInputInternetConnection: LiveData<Boolean>
        get() = _errorInputInternetConnection

    private val getAllAppointmentForUserUseCase = GetAllAppointmentForUserUseCase(HospitalRepositoryImpl())
    private val deleteAppointmentUseCase = DeleteAppointmentUseCase(HospitalRepositoryImpl())

    fun getAllAppointments(id:Long){
        viewModelScope.launch {
            if(isInternetAvailable()){
                _errorInputInternetConnection.value = false
                _appointmentList.value= getAllAppointmentForUserUseCase.invoke(id)
            }else{
                _errorInputInternetConnection.value = true
            }

        }

    }

    fun deleteAppointment(id_deleted:Long, id_patient:Long){
        viewModelScope.launch {
            if(isInternetAvailable()) {
                _errorInputInternetConnection.value = false
                _appointmentList.value = deleteAppointmentUseCase.invoke(id_deleted,id_patient)
            }else{
                _errorInputInternetConnection.value = true
            }
        }

    }


    fun isInternetAvailable(): Boolean {
        val cm = ContextCompat.getSystemService(
            getApplication<Application>().applicationContext,
            ConnectivityManager::class.java
        )

        return cm!!.activeNetworkInfo != null && cm!!.activeNetworkInfo!!.isConnected
    }
}