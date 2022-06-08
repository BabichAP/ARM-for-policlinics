package ru.mirea.armforpolyclinics.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.databinding.FragmentLoginBinding
import ru.mirea.armforpolyclinics.domain.usecase.LoginUseCase
import ru.mirea.armforpolyclinics.presentation.viewmodel.LoginViewModel


class LoginFragment : Fragment() {

    private lateinit var _binding:FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding.root
    }
    private lateinit var prefs: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs =requireActivity().applicationContext.getSharedPreferences(R.string.app_name.toString(), Context.MODE_PRIVATE)
        loginViewModel = LoginViewModel(requireActivity().application)
        addTextListeners()
        addTextErrorListener()
        addButtonListener()
        addLoginStateListener()
        addConnectionToInternetListener()


        _binding.toRegisterTextview.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

    }

    private fun addConnectionToInternetListener() {
        loginViewModel.errorInputInternetConnection.observe(viewLifecycleOwner){
            if(it){
             Toast.makeText(context,"Проблемы с интернетом",Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun addLoginStateListener() {
        loginViewModel.isLoggedIn.observe(viewLifecycleOwner){
            when(it){
                LoginUseCase.LOGIN_SUCCESS->{Toast.makeText(context, LoginUseCase.LOGIN_SUCCESS, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_patientListFragment)}
                LoginUseCase.LOGIN_IS_NOT_SUCCESS_API->Toast.makeText(context,LoginUseCase.LOGIN_IS_NOT_SUCCESS_API,Toast.LENGTH_LONG).show()
                LoginUseCase.LOGIN_IS_NOT_SUCCESS -> Toast.makeText(context, LoginUseCase.LOGIN_IS_NOT_SUCCESS,Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun addButtonListener() {
        _binding.signInButton.setOnClickListener {

            loginViewModel.login(
                _binding.passwordEdittext.text.toString(),
                _binding.emailAddressEdittext.text.toString()
            )
        }
    }

    private fun addTextErrorListener(){
    loginViewModel.errorInputPassword.observe(viewLifecycleOwner){
        if (it) {
            _binding.llPasswordEdittext.errorIconDrawable= null
            _binding.passwordEdittext.error = getString(R.string.error_password_hint)
            _binding.llPasswordEdittext.error = getString(R.string.llerror_password_hint)
        }else{
            _binding.llPasswordEdittext.error = null
        }
    }
        loginViewModel.errorInputEmail.observe(viewLifecycleOwner){
            if (it) {
                _binding.llEmailAddressEdittext.errorIconDrawable = null
                _binding.emailAddressEdittext.error = getString(R.string.error_email_hint)
                _binding.llEmailAddressEdittext.error = getString(R.string.llerror_email_hint)
            }else{

                _binding.llEmailAddressEdittext.error = null
        }
        }

    }

    private fun addTextListeners(){
        _binding.passwordEdittext.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loginViewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        _binding.emailAddressEdittext.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loginViewModel.resetErrorInputEmail()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

}