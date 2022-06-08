package ru.mirea.armforpolyclinics.data


import com.example.hospital.model.BloodTypes
import com.example.hospital.pojo.MessageResponse
import retrofit2.Response
import ru.mirea.armforpolyclinics.data.pojo.JwtResponse
import ru.mirea.armforpolyclinics.data.pojo.LoginRequest
import ru.mirea.armforpolyclinics.domain.entity.*
import ru.mirea.armforpolyclinics.domain.repository.HospitalRepository
import ru.mirea.armforpolyclinics.domain.usecase.*
import java.util.*

class HospitalRepositoryImpl: HospitalRepository {



    override suspend fun addNewAppointment(appointment: Appointment): String {
        var a:Response<Appointment>?
        try {
            a = ApiInterface.create().addAppointment(appointment,"Bearer ${TokenManager.getToken()}" )
        }catch (e:Exception){
            a = Response.success(243,Appointment(0, "Date(0,0,0,0,0)","",Profession.Dentist,"",0))
        }

        val response = a
        val code = response?.code()
        val message:String = if (response?.body() == null){
            if (code == 243){
                AddNewAppointmentUseCase.ADD_APPOINTMENT_IS_NOT_SUCCESS_API
            }else{
                AddNewAppointmentUseCase.ADD_APPOINTMENT_IS_NOT_SUCCESS
            }
        }else{
            if (code == 243){
                AddNewAppointmentUseCase.ADD_APPOINTMENT_IS_NOT_SUCCESS_API
            }else{
                AddNewAppointmentUseCase.ADD_APPOINTMENT_SUCCESS
            }
        }
        return message
    }

    override suspend fun addNewPatient(patient: Patient): String {
        var a:Response<Patient>?
        try {
            a = ApiInterface.create().addPatient(patient,"Bearer ${TokenManager.getToken()}" )
        }catch (e:Exception){
            a = Response.success(243,Patient(0, "Date(0,0,0,0,0)","das","fsa",Genders.Male,"asd","1234567897","45646546"," ",BloodTypes.Fourth,25))
        }

        val response = a
        val code = response?.code()
        val message:String = if (response?.body() == null){
            if (code == 243){
                AddNewPatientUseCase.ADD_PATIENT_IS_NOT_SUCCESS_API
            }else{
                AddNewPatientUseCase.ADD_PATIENT_IS_NOT_SUCCESS
            }
        }else{
            if (code == 243){
                AddNewPatientUseCase.ADD_PATIENT_IS_NOT_SUCCESS_API
            }else{
                AddNewPatientUseCase.ADD_PATIENT_SUCCESS
            }
        }
        return message
    }

    override suspend fun getAllDoctors(): Pair<String, List<Doctor>?> {
        var a:Response<List<Doctor>>?

        try {
            a = ApiInterface.create().getAllDoctors("Bearer ${TokenManager.getToken()}" )
        }catch (e:Exception){
            a = Response.success(243, listOf())
        }

        val response = a
        val code = response?.code()
        val message:String = if (response?.body() == null){
            if (code == 243){
                GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API
            }else{
                GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS
            }
        }else{
            if (code == 243){
                GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API
            }else{
                GetAllDoctorsUseCase.DOWNLOAD_SUCCESS
            }
        }
        return Pair(message,a?.body())
    }

    override suspend fun getAllPatients(): Pair<String, List<Patient>?> {
        var a:Response<List<Patient>?>?

        try {
            a = ApiInterface.create().getAllPatients("Bearer ${TokenManager.getToken()}" )
        }catch (e:Exception){
            a = Response.success(243, listOf())
        }

        val response = a
        val code = response?.code()
        val message:String = if (response?.body() == null){
            if (code == 243){
                GetAllPatientsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API
            }else{
                GetAllPatientsUseCase.DOWNLOAD_IS_NOT_SUCCESS
            }
        }else{
            if (code == 243){
                GetAllPatientsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API
            }else{
                GetAllPatientsUseCase.DOWNLOAD_SUCCESS
            }
        }
        return Pair(message,a?.body())
    }

    override suspend fun getAllAppointment(id: Long): Pair<String, List<Appointment>?> {
        var a:Response<List<Appointment>?>?

        try {
            a = ApiInterface.create().getAllAppointmentByPatientId(id,"Bearer ${TokenManager.getToken()}" )
        }catch (e:Exception){
            a = Response.success(243, listOf())
        }

        val response = a
        val code = response?.code()
        val message:String = if (response?.body() == null){
            if (code == 243){
                GetAllAppointmentForUserUseCase.DOWNLOAD_IS_NOT_SUCCESS_API
            }else{
                GetAllAppointmentForUserUseCase.DOWNLOAD_IS_NOT_SUCCESS
            }
        }else{
            if (code == 243){
                GetAllAppointmentForUserUseCase.DOWNLOAD_IS_NOT_SUCCESS_API
            }else{
                GetAllAppointmentForUserUseCase.DOWNLOAD_SUCCESS
            }
        }
        return Pair(message,a?.body())
    }


    override suspend fun deletePatient(id: Long): Pair<String, List<Patient>?> {
        ApiInterface.create().deletePatient(id,"Bearer ${TokenManager.getToken()}")
        return getAllPatients()
    }

    override suspend fun deleteAppointment(id_deleted: Long, id_patient:Long): Pair<String, List<Appointment>?> {
        ApiInterface.create().deleteAppointment(id_deleted,"Bearer ${TokenManager.getToken()}")
        return getAllAppointment(id_patient)
    }

    override suspend fun login(email: String, password: String):String {
        var a:Response<JwtResponse>?
        try {
            a = ApiInterface.create().login(LoginRequest(email,password))

        }catch (e:Exception){
            a = Response.success(243,JwtResponse(token = "ERROR",id = "dfs", email = " ", roles = listOf()))
        }

        val code = a?.code()
        val message:String
        if (a?.body() == null){
            message = if (code == 243){
                LoginUseCase.LOGIN_IS_NOT_SUCCESS_API
            }else{
                LoginUseCase.LOGIN_IS_NOT_SUCCESS
            }

        }else{
            if (code == 243){
                message = LoginUseCase.LOGIN_IS_NOT_SUCCESS_API
            }else{
                message = LoginUseCase.LOGIN_SUCCESS
                TokenManager.storeToken(a.body()?.token!!)
            }
        }
        return message

    }


    override suspend fun register(employee: Employee): String {
        var a:Response<MessageResponse>?
        try {
            a = ApiInterface.create().register(employee.build(employee))
        }catch (e:Exception){
            a = Response.success(243,MessageResponse(""))
        }

        val response = a
        val code = response?.code()
        val message:String = if (response?.body() == null){
            if (code == 243){
                RegisterUseCase.REGISTER_IS_NOT_SUCCESS_API
            }else{
                RegisterUseCase.REGISTER_IS_NOT_SUCCESS
            }
        }else{
            if (code == 243){
                RegisterUseCase.REGISTER_IS_NOT_SUCCESS_API
            }else{
                RegisterUseCase.REGISTER_SUCCESS
            }
        }
        return message
    }

}
