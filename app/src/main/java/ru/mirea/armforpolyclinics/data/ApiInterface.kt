package ru.mirea.armforpolyclinics.data

import com.example.hospital.pojo.MessageResponse
import ru.mirea.armforpolyclinics.data.pojo.LoginRequest
import ru.mirea.armforpolyclinics.data.pojo.JwtResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.mirea.armforpolyclinics.data.pojo.SignUpRequest
import ru.mirea.armforpolyclinics.domain.entity.Appointment
import ru.mirea.armforpolyclinics.domain.entity.Doctor
import ru.mirea.armforpolyclinics.domain.entity.Employee
import ru.mirea.armforpolyclinics.domain.entity.Patient

interface ApiInterface {

    @POST("/login")
    suspend fun login(@Body signInRequest: LoginRequest, ) : Response<JwtResponse>?

    @POST("/patient/add")
    suspend fun addPatient(@Body patient: Patient,@Header("Authorization") token:String ):Response<Patient>?

    @GET("/patient/delete/")
    suspend fun deletePatient(@Query("id") id:Long,@Header("Authorization") token:String )

    @GET("/appointment/allById/")
    suspend fun getAllAppointmentByPatientId(@Query("id") id:Long,@Header("Authorization") token:String):Response<List<Appointment>?>

    @GET("/doctor/all")
    suspend fun getAllDoctors(@Header("Authorization") token:String):Response<List<Doctor>>?

    @POST("/appointment/add")
    suspend fun addAppointment(@Body appointment: Appointment,@Header("Authorization") token:String):Response<Appointment>?

    @GET("/appointment/delete")
    suspend fun deleteAppointment(@Query("id") id:Long,@Header("Authorization") token:String )

    @GET("/patient/all")
    suspend fun getAllPatients(@Header("Authorization") token:String):Response<List<Patient>?>

    @POST("/register")
    suspend fun register(@Body signUpRequest: SignUpRequest) : Response<MessageResponse>

    companion object {

       private var BASE_URL = "http://192.168.1.49:8080/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}