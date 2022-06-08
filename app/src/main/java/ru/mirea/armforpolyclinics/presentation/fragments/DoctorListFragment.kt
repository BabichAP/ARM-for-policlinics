package ru.mirea.armforpolyclinics.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.databinding.FragmentDoctorListBinding
import ru.mirea.armforpolyclinics.domain.usecase.GetAllDoctorsUseCase
import ru.mirea.armforpolyclinics.domain.usecase.GetAllPatientsUseCase
import ru.mirea.armforpolyclinics.presentation.adapter.DoctorListAdapter
import ru.mirea.armforpolyclinics.presentation.adapter.PatientListAdapter
import ru.mirea.armforpolyclinics.presentation.viewmodel.DoctorListViewModel

class DoctorListFragment : Fragment() {

    lateinit var _binding:FragmentDoctorListBinding
    lateinit var viewModel:DoctorListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorListBinding.inflate(inflater,container,false)
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = DoctorListViewModel(requireActivity().application)
        val adapter = DoctorListAdapter()
        addButtonListeners()
        addConnectionToInternetListener()
        viewModel.getAllDoctors()
        setupListObserver(adapter)
    }

    fun addButtonListeners(){
        _binding.returnPatientButton.setOnClickListener{
            findNavController().navigate(R.id.action_doctorListFragment_to_patientListFragment)
        }
    }

    private fun addConnectionToInternetListener() {
        viewModel.errorInputInternetConnection.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(context,"Проблемы с интернетом", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun setupListObserver(adapter: DoctorListAdapter){
        viewModel.doctorList.observe(viewLifecycleOwner){
            when(it.first){
                GetAllDoctorsUseCase.DOWNLOAD_SUCCESS->{
                    Toast.makeText(context, GetAllDoctorsUseCase.DOWNLOAD_SUCCESS,Toast.LENGTH_SHORT).show()
                    adapter.submitList(it.second)
                    _binding.doctorList.adapter = adapter
                }
                GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS -> Toast.makeText(context, GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS,Toast.LENGTH_SHORT).show()
                GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API->Toast.makeText(context, GetAllDoctorsUseCase.DOWNLOAD_IS_NOT_SUCCESS_API,Toast.LENGTH_SHORT).show()
            }
            _binding.swipeRefreshLayout.isRefreshing = false
        }
    }

}