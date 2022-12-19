package com.example.havetodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.havetodo.PreferenceData.LoginPreference
import com.example.havetodo.ViewModels.LoginViewModel
import com.example.havetodo.databinding.FragmentLogInBinding
import com.example.havetodo.entities.UserModel
import kotlinx.coroutines.launch

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding
    private lateinit var preference: LoginPreference
    private val loginViewModel : LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preference = LoginPreference(requireContext())
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        loginViewModel.errMsgLD.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            // TODO: validate fields
            loginViewModel.login(email, password) {
                lifecycle.coroutineScope.launch {
                    preference.setLoginStatus(true, it, requireContext())
                    findNavController().popBackStack()
                }
            }
        }
        binding.registerBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            // TODO: validate fields
            val user = UserModel(email = email, password = password)
            loginViewModel.register(user) {
                lifecycle.coroutineScope.launch {
                    preference.setLoginStatus(true, it, requireContext())
                    findNavController().popBackStack()
                }
            }
        }
        return binding.root
    }

}