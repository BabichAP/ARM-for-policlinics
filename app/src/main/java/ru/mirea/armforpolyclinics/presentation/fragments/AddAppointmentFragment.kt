package ru.mirea.armforpolyclinics.presentation.fragments

import android.icu.util.LocaleData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.data.HospitalRepositoryImpl
import ru.mirea.armforpolyclinics.databinding.FragmentAddAppointmentBinding
import ru.mirea.armforpolyclinics.domain.entity.Doctor
import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.entity.Profession
import ru.mirea.armforpolyclinics.domain.usecase.AddNewAppointmentUseCase
import ru.mirea.armforpolyclinics.domain.usecase.GetAllDoctorsUseCase
import ru.mirea.armforpolyclinics.domain.usecase.GetAllPatientsUseCase
import ru.mirea.armforpolyclinics.domain.usecase.RegisterUseCase
import ru.mirea.armforpolyclinics.presentation.viewmodel.AddAppointmentFragmentViewModel
import java.util.*

class AddAppointmentFragment : Fragment() {
    private val  args by navArgs<PatientInfoFragmentArgs>()
    lateinit var _binding:FragmentAddAppointmentBinding
    lateinit var viewModel:AddAppointmentFragmentViewModel
    lateinit var patient:Patient
    lateinit var doctors:List<Doctor>
    lateinit var doctorsNames:MutableList<String>
    private var date:String = "2-11-2022"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAppointmentBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        patient = args.patient
        viewModel = AddAppointmentFragmentViewModel(requireActivity().application)
        doctorsNames = mutableListOf()
        viewModel.getAllDoctors()
        var adapter:ArrayAdapter<String>
        viewModel.doctorsList.observe(viewLifecycleOwner){
            when(it.first){
                GetAllDoctorsUseCase.DOWNLOAD_SUCCESS->{
                    doctors = it.second!!
                    doctors.forEach {
                        doctorsNames.add("${it.name} ${when(it.profession){
                            Profession.Dentist -> "Дантист"
                            Profession.Surgeon -> "Хирург"
                        }                        }")
                    }
                    adapter  = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,android.R.id.text1,doctorsNames)

                    _binding.doctor.adapter = adapter
                    _binding.doctor.setSelection(0)

                }
                GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS -> Toast.makeText(context, GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS,
                    Toast.LENGTH_SHORT).show()
                GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API-> Toast.makeText(context, GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API,
                    Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isAdded.observe(viewLifecycleOwner){
            when(it){
                AddNewAppointmentUseCase.ADD_APPOINTMENT_SUCCESS->{Toast.makeText(context, AddNewAppointmentUseCase.ADD_APPOINTMENT_SUCCESS, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(AddAppointmentFragmentDirections.actionAddAppointmentFragmentToPatientInfoFragment(patient))}
                AddNewAppointmentUseCase.ADD_APPOINTMENT_IS_NOT_SUCCESS_API->Toast.makeText(context,
                    AddNewAppointmentUseCase.ADD_APPOINTMENT_IS_NOT_SUCCESS_API,Toast.LENGTH_LONG).show()
                AddNewAppointmentUseCase.ADD_APPOINTMENT_IS_NOT_SUCCESS -> Toast.makeText(context, AddNewAppointmentUseCase.ADD_APPOINTMENT_IS_NOT_SUCCESS,Toast.LENGTH_LONG).show()
            }

        }

        _binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = "$dayOfMonth-${month+1}-$year"
        }

        addButtonListener()


    }

    private fun addButtonListener() {
        _binding.addAppointmentButton.setOnClickListener {


            viewModel.addAppointment(
                patient,
                doctors.get(_binding.doctor.selectedItemId.toInt()),
                date
            )
        }
    }


}