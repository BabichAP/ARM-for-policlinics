package ru.mirea.armforpolyclinics.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.databinding.FragmentPatientListBinding
import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.usecase.GetAllPatientsUseCase
import ru.mirea.armforpolyclinics.presentation.adapter.PatientListAdapter
import ru.mirea.armforpolyclinics.presentation.viewmodel.PatientListViewModel

class PatientListFragment : Fragment() {


    private lateinit var patientViewModel: PatientListViewModel
    private lateinit var _binding: FragmentPatientListBinding
  

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentPatientListBinding.inflate(inflater)
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PatientListAdapter()
        patientViewModel = PatientListViewModel(requireActivity().application)
        addConnectionToInternetListener()
        setupListObserver(adapter)
        setupClickListeners(adapter)
        patientViewModel.getAllPatients()

    }

    private fun setupListObserver(adapter: PatientListAdapter){
        patientViewModel.patientList.observe(viewLifecycleOwner){
            when(it.first){
                GetAllPatientsUseCase.DOWNLOAD_SUCCESS->{
                    Toast.makeText(context, GetAllPatientsUseCase.DOWNLOAD_SUCCESS,Toast.LENGTH_SHORT).show()
                    adapter.submitList(it.second)
                    _binding.patientList.adapter = adapter
                    _binding.swipeRefreshLayout.isRefreshing = false
                }
                GetAllPatientsUseCase.DOWNLOAD_IS_NOT_SUCCESS -> Toast.makeText(context, GetAllPatientsUseCase.DOWNLOAD_IS_NOT_SUCCESS,Toast.LENGTH_SHORT).show()
                GetAllPatientsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API->Toast.makeText(context, GetAllPatientsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API,Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun addConnectionToInternetListener() {
        patientViewModel.errorInputInternetConnection.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(context,"Проблемы с интернетом",Toast.LENGTH_LONG).show()
            }

        }
    }


    private fun setupClickListeners(adapter: PatientListAdapter){


        _binding.addPatientButton.setOnClickListener{
            findNavController().navigate(R.id.action_patientListFragment_to_addPatientFragment)
        }
        _binding.doctorButton.setOnClickListener{
            findNavController().navigate(R.id.action_patientListFragment_to_doctorListFragment)
        }

        adapter.onMenuClickListener = object :PatientListAdapter.OnMenuClickListener{
            override fun onMenuClick(patient: Patient, view:View) {

                val popupMenu = PopupMenu(context,view)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.detail_patient -> {findNavController().navigate(PatientListFragmentDirections.actionPatientListFragmentToPatientInfoFragment(patient))
                            true}
                        R.id.delete_patient ->{
                            patientViewModel.deletePatient(patient.id)
                            true}
                        else -> false
                    }
                }
                popupMenu.inflate(R.menu.card_menu)
                popupMenu.show()
            }
        }


        adapter.onPatientClickListener = object :PatientListAdapter.OnPatientClickListener{
            override fun onPatientClickListener(patient: Patient) {
                findNavController().navigate(PatientListFragmentDirections.actionPatientListFragmentToPatientInfoFragment(patient))
            }

        }
    }

}