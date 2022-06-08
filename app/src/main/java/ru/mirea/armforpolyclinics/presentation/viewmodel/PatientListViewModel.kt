package ru.mirea.armforpolyclinics.presentation.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mirea.armforpolyclinics.data.HospitalRepositoryImpl
import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.usecase.DeletePatientUseCase
import ru.mirea.armforpolyclinics.domain.usecase.GetAllPatientsUseCase
import ru.mirea.armforpolyclinics.domain.usecase.LoginUseCase

class PatientListViewModel(application: Application): AndroidViewModel(application) {

    private val _patientList: MutableLiveData<Pair<String,List<Patient>?>> = MutableLiveData()
    val patientList: LiveData<Pair<String,List<Patient>?>>
        get()=_patientList

    private val _errorInputInternetConnection = MutableLiveData<Boolean>()
    val errorInputInternetConnection: LiveData<Boolean>
        get() = _errorInputInternetConnection

    private val getAllPatientsUseCase = GetAllPatientsUseCase(HospitalRepositoryImpl())
    private val deletePatientUseCase = DeletePatientUseCase(HospitalRepositoryImpl())

    fun getAllPatients(){
        viewModelScope.launch {
            if(isInternetAvailable()){
                _errorInputInternetConnection.value = false
                _patientList.value= getAllPatientsUseCase.invoke()
            }else{
                _errorInputInternetConnection.value = true
            }

        }

    }

    fun deletePatient(id:Long){
        viewModelScope.launch {
            if(isInternetAvailable()) {
                _errorInputInternetConnection.value = false
                _patientList.value = deletePatientUseCase.invoke(id)
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