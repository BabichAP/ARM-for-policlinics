package ru.mirea.armforpolyclinics.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hospital.model.BloodTypes
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.data.HospitalRepositoryImpl
import ru.mirea.armforpolyclinics.databinding.FragmentPatientInfoBinding
import ru.mirea.armforpolyclinics.domain.entity.Appointment
import ru.mirea.armforpolyclinics.domain.entity.Genders
import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.usecase.GetAllAppointmentForUserUseCase
import ru.mirea.armforpolyclinics.domain.usecase.GetAllPatientsUseCase
import ru.mirea.armforpolyclinics.presentation.adapter.AppointmentListAdapter
import ru.mirea.armforpolyclinics.presentation.adapter.PatientListAdapter
import ru.mirea.armforpolyclinics.presentation.viewmodel.PatientInfoViewModel
import ru.mirea.armforpolyclinics.presentation.viewmodel.PatientListViewModel

class PatientInfoFragment : Fragment() {

   private lateinit var _binding:FragmentPatientInfoBinding
   private val  args by navArgs<PatientInfoFragmentArgs>()
    lateinit var patient: Patient
    lateinit var patientViewModel:PatientInfoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        patient = args.patient
        _binding = FragmentPatientInfoBinding.inflate(inflater)
        return _binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        patientViewModel = PatientInfoViewModel(requireActivity().application)
        addConnectionToInternetListener()
        patientViewModel.getAllAppointments(patient.id)
        val adapter = AppointmentListAdapter()
        patientViewModel.getAllAppointments(patient.id)
        patientViewModel.appointmentList.observe(viewLifecycleOwner){
            when(it.first){
                GetAllAppointmentForUserUseCase.DOWNLOAD_SUCCESS->{
                    Toast.makeText(context, GetAllAppointmentForUserUseCase.DOWNLOAD_SUCCESS,Toast.LENGTH_SHORT).show()
                    adapter.submitList(it.second)
                    _binding.recyclerview.adapter = adapter
                }
                GetAllAppointmentForUserUseCase.DOWNLOAD_IS_NOT_SUCCESS -> Toast.makeText(context, GetAllAppointmentForUserUseCase.DOWNLOAD_IS_NOT_SUCCESS,Toast.LENGTH_SHORT).show()
                GetAllAppointmentForUserUseCase.DOWNLOAD_IS_NOT_SUCCESS_API->Toast.makeText(context, GetAllAppointmentForUserUseCase.DOWNLOAD_IS_NOT_SUCCESS_API,Toast.LENGTH_SHORT).show()
            }
        }
        _binding.fullNameLabel.text = "${patient.name} ${patient.surname} ${patient.patronymic}"
        _binding.addressText.text = "Адреc: ${patient.address}"
        _binding.ageText.text = "Возраст: ${patient.age}"
        _binding.snilsText.text = "Снилс: ${patient.snils}"
        _binding.policyText.text = "Полис: ${patient.policyNumber}"
        _binding.phoneText.text = "Телефон: ${patient.phoneNumber}"
        _binding.genderText.text = "Пол: ${when(patient.gender){
            Genders.Male -> "МУЖ"
            Genders.Female -> "ЖЕН"
            Genders.None -> "НЕ УКАЗАН"
        }        }"
        _binding.bloodText.text = "Группа крови: ${when(patient.bloodType){
            BloodTypes.First->"Первая"
            BloodTypes.Second->"Вторая"
            BloodTypes.Third->"Третья"
            BloodTypes.Fourth->"Четвёртая"

        }        }"

        adapter.onMenuClickListener = object :AppointmentListAdapter.OnMenuClickListener{
            override fun onMenuClick(apponintment: Appointment, view:View) {

                val popupMenu = PopupMenu(context,view)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.delete_appoint ->{
                            patientViewModel.deleteAppointment(apponintment.id,patient.id)
                            true}

                        else -> false
                    }
                }
                popupMenu.inflate(R.menu.appointment_menu)
                popupMenu.show()
            }
        }


        _binding.addAppointmentButton.setOnClickListener{
            findNavController().navigate(PatientInfoFragmentDirections.actionPatientInfoFragmentToAddAppointmentFragment(patient))
        }

    }
    private fun addConnectionToInternetListener() {
        patientViewModel.errorInputInternetConnection.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(context,"Проблемы с интернетом",Toast.LENGTH_LONG).show()
            }

        }
    }

}