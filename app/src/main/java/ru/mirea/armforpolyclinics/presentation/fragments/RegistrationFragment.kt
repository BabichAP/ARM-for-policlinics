package ru.mirea.armforpolyclinics.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.databinding.FragmentRegistrationBinding
import ru.mirea.armforpolyclinics.domain.usecase.LoginUseCase
import ru.mirea.armforpolyclinics.domain.usecase.RegisterUseCase
import ru.mirea.armforpolyclinics.presentation.viewmodel.RegistrationViewModel


class RegistrationFragment : Fragment() {

    private lateinit var _binding: FragmentRegistrationBinding
    private lateinit var viewModel:RegistrationViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = RegistrationViewModel(requireActivity().application)
        addConnectionToInternetListener()
        addRegisterStateListener()
        addButtonListener()
        addTextErrorListener()
        addTextListeners()
        _binding.toAuthTextview.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }

    private fun addRegisterStateListener() {
        viewModel.isRegister.observe(viewLifecycleOwner){
            when(it){
                RegisterUseCase.REGISTER_SUCCESS->{Toast.makeText(context, RegisterUseCase.REGISTER_SUCCESS, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)}
                RegisterUseCase.REGISTER_IS_NOT_SUCCESS_API->Toast.makeText(context,
                    RegisterUseCase.REGISTER_IS_NOT_SUCCESS_API,Toast.LENGTH_LONG).show()
                RegisterUseCase.REGISTER_IS_NOT_SUCCESS -> Toast.makeText(context, RegisterUseCase.REGISTER_IS_NOT_SUCCESS,Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun addConnectionToInternetListener() {
        viewModel.errorInputInternetConnection.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(context,"Проблемы с интернетом", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun addButtonListener() {
        _binding.signUpButton.setOnClickListener {

            viewModel.register(
                _binding.nameEdittextRegister.text.toString(),
                _binding.surnameEdittextRegister.text.toString(),
                _binding.patronymicEdittextRegister.text.toString(),
                _binding.phoneEdittextRegister.text.toString(),
                _binding.emailEdittextRegister.text.toString(),
                _binding.passwordEdittextRegister.text.toString()

            )
        }
    }

    private fun addTextErrorListener(){
        viewModel.errorInputPhoneNumber.observe(viewLifecycleOwner){
            if (it) {
                _binding.llPhoneEdittextRegister.errorIconDrawable= null
                _binding.phoneEdittextRegister.error = getString(R.string.error_phone_hint)
                _binding.llPhoneEdittextRegister.error = getString(R.string.llerror_phone_hint)
            }else{
                _binding.llPhoneEdittextRegister.error = null
            }
        }
        viewModel.errorInputPatronymic.observe(viewLifecycleOwner){
            if (it) {
                _binding.llPatronymicEdittextRegister.errorIconDrawable= null
                _binding.patronymicEdittextRegister.error = getString(R.string.error_patronymic_hint)
                _binding.llPatronymicEdittextRegister.error = getString(R.string.llerror_patronymic_hint)
            }else{
                _binding.llPatronymicEdittextRegister.error = null
            }
        }
        viewModel.errorInputSurName.observe(viewLifecycleOwner){
            if (it) {
                _binding.llSurnameEdittextRegister.errorIconDrawable= null
                _binding.surnameEdittextRegister.error = getString(R.string.error_surname_hint)
                _binding.llSurnameEdittextRegister.error = getString(R.string.llerror_surname_hint)
            }else{
                _binding.llSurnameEdittextRegister.error = null
            }
        }
        viewModel.errorInputName.observe(viewLifecycleOwner){
            if (it) {
                _binding.llNameEdittextRegister.errorIconDrawable= null
                _binding.nameEdittextRegister.error = getString(R.string.error_name_hint)
                _binding.llNameEdittextRegister.error = getString(R.string.llerror_name_hint)
            }else{
                _binding.llNameEdittextRegister.error = null
            }
        }
        viewModel.errorInputPassword.observe(viewLifecycleOwner){
            if (it) {
                _binding.llPasswordEdittextRegister.errorIconDrawable= null
                _binding.passwordEdittextRegister.error = getString(R.string.error_password_hint)
                _binding.llPasswordEdittextRegister.error = getString(R.string.llerror_password_hint)
            }else{
                _binding.llPasswordEdittextRegister.error = null
            }
        }
        viewModel.errorInputEmail.observe(viewLifecycleOwner){
            if (it) {
                _binding.llEmailEdittextRegister.errorIconDrawable = null
                _binding.emailEdittextRegister.error = getString(R.string.error_email_hint)
                _binding.llEmailEdittextRegister.error = getString(R.string.llerror_email_hint)
            }else{

                _binding.llEmailEdittextRegister.error = null
            }
        }

    }

    private fun addTextListeners(){
        _binding.passwordEdittextRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        _binding.emailEdittextRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputEmail()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        _binding.nameEdittextRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        _binding.surnameEdittextRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputSurname()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        _binding.patronymicEdittextRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPatronymic()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        _binding.phoneEdittextRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPhoneNumber()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }



}