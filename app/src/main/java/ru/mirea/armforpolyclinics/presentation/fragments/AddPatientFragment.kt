package ru.mirea.armforpolyclinics.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hospital.model.BloodTypes
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.databinding.FragmentAddPatientBinding
import ru.mirea.armforpolyclinics.domain.entity.Genders
import ru.mirea.armforpolyclinics.domain.usecase.AddNewPatientUseCase
import ru.mirea.armforpolyclinics.domain.usecase.RegisterUseCase
import ru.mirea.armforpolyclinics.presentation.viewmodel.AddPatientViewModel


class AddPatientFragment : Fragment() {

    lateinit var _binding:FragmentAddPatientBinding
    lateinit var viewModel: AddPatientViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPatientBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = AddPatientViewModel(requireActivity().application)
        //addButtonListener()
        val adapterGenders: ArrayAdapter<String>
        val adapterBlood: ArrayAdapter<String>
        val genderArray :MutableList<String> = mutableListOf()
        val bloodArray :MutableList<String> = mutableListOf()
        Genders.values().forEach { when(it){
            Genders.Male -> genderArray.add("МУЖ")
            Genders.Female -> genderArray.add("ЖЕН")
            Genders.None ->genderArray.add("НЕТ")
        } }

        BloodTypes.values().forEach { when(it){
            BloodTypes.Fourth -> bloodArray.add("Четвёртая")
            BloodTypes.Third -> bloodArray.add("Третья")
            BloodTypes.Second->bloodArray.add("Вторая")
            BloodTypes.First->bloodArray.add("Первая")
        } }
        adapterGenders  = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,android.R.id.text1,genderArray)
        adapterBlood  = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,android.R.id.text1,bloodArray)
        _binding.bloodText.adapter = adapterBlood
        _binding.genderText.adapter = adapterGenders
        _binding.bloodText.setSelection(0)
        _binding.genderText.setSelection(0)
        addButtonListener()


        viewModel.isAdded.observe(viewLifecycleOwner){
            when(it){
                AddNewPatientUseCase.ADD_PATIENT_SUCCESS->{
                    Toast.makeText(context, AddNewPatientUseCase.ADD_PATIENT_SUCCESS, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addPatientFragment_to_patientListFragment)}
                AddNewPatientUseCase.ADD_PATIENT_IS_NOT_SUCCESS_API-> Toast.makeText(context,
                    AddNewPatientUseCase.ADD_PATIENT_IS_NOT_SUCCESS_API, Toast.LENGTH_LONG).show()
                AddNewPatientUseCase.ADD_PATIENT_IS_NOT_SUCCESS -> Toast.makeText(context, AddNewPatientUseCase.ADD_PATIENT_IS_NOT_SUCCESS,
                    Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun addButtonListener() {
        _binding.signUpButton.setOnClickListener {

            viewModel.addNewPatient(
                _binding.nameEdittextAdd.text.toString(),
                _binding.surnameEdittextAdd.text.toString(),
                _binding.patronymicEdittextAdd.text.toString(),
                when(_binding.genderText.selectedItem.toString()){
                    "ЖЕН"->Genders.Female
                    "МУЖ"->Genders.Female
                    else->Genders.None
                },
                _binding.addressEdittextAdd.text.toString(),
                _binding.phoneEdittextAdd.text.toString(),
                _binding.snilsEdittextRegister.text.toString(),
                _binding.policyEdittextRegister.text.toString(),
               when( _binding.bloodText.selectedItem.toString()){
                   "Четвёртая" ->BloodTypes.Fourth
                   "Первая" -> BloodTypes.First
                   "Третья" -> BloodTypes.Third
                   else -> BloodTypes.Second
               },
                _binding.ageEdittextRegister.text.toString().toIntOrNull()!!


            )
        }
    }

}