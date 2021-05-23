package com.example.isuscorp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.isuscorp.IsusCorpApp
import com.example.isuscorp.R
import com.example.isuscorp.data.model.User
import com.example.isuscorp.databinding.FragmentRegistrerBinding
import com.example.isuscorp.databinding.LoginFragmentBinding

class RegistrerFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModel.LoginViewModelFactory((activity?.application as IsusCorpApp).repository)
    }
    private lateinit var binding: FragmentRegistrerBinding

    lateinit var userName: String
    lateinit var lastName: String
    lateinit var firstName: String
    lateinit var password: String
    lateinit var repeatPassword: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registrer, container, false)

        binding.addUser.setOnClickListener {
            insertUser()
        }

        return binding.root
    }

    //get data user form to insert in data base
    private fun insertUser(){
        userName = binding.userRegistrerEdit.text.toString()
        firstName = binding.firstnameRegistrerEdit.text.toString()
        lastName = binding.lastnameRegistrerEdit.text.toString()
        password = binding.passworRegistrerdEdit.text.toString()
        repeatPassword = binding.passworRepeatRegistrerdEdit.text.toString()

        if(validateForm()){
            val user = User(0,userName,firstName,lastName,password)
            loginViewModel.insert(user)
            binding.root.findNavController().popBackStack()
        }
    }

    //validate user form
    private fun validateForm(): Boolean{
        when {
            userName.isEmpty() -> {
                binding.userRegistrerField.error = this.getString(R.string.not_empty)
                return false
            }
            firstName.isEmpty() -> {
                binding.firstnameRegistrerField.error = this.getString(R.string.not_empty)
                return false
            }
            lastName.isEmpty() -> {
                binding.lastnameRegistrerField.error = this.getString(R.string.not_empty)
                return false
            }
            password.isEmpty() -> {
                binding.passwordRegistrerField.error = this.getString(R.string.not_empty)
                return false
            }
            repeatPassword != password -> {
                binding.passworRepeatdRegistrerField.error = this.getString(R.string.no_macht)
                return false
            }
            else -> {
                return true
            }
        }
    }
}