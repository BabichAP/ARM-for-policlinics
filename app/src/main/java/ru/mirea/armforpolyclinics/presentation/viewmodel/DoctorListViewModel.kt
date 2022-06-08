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
import ru.mirea.armforpolyclinics.domain.entity.Doctor
import ru.mirea.armforpolyclinics.domain.usecase.GetAllDoctorsUseCase

class DoctorListViewModel(application: Application):AndroidViewModel(application) {

    private val _doctorList: MutableLiveData<Pair<String, List<Doctor>?>> = MutableLiveData()
    val doctorList: LiveData<Pair<String, List<Doctor>?>>
        get()=_doctorList

    private val _errorInputInternetConnection = MutableLiveData<Boolean>()
    val errorInputInternetConnection: LiveData<Boolean>
        get() = _errorInputInternetConnection


    private val getAllDoctorsUseCase = GetAllDoctorsUseCase(HospitalRepositoryImpl())


    fun getAllDoctors(){
        viewModelScope.launch {
            if(isInternetAvailable()){
                _errorInputInternetConnection.value = false
                _doctorList.value= getAllDoctorsUseCase.invoke()
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