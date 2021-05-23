package com.example.isuscorp.ui.login

import androidx.lifecycle.*
import com.example.isuscorp.data.database.Repository
import com.example.isuscorp.data.model.User
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    lateinit var user: User

    //Get user from database
    fun getUser(userName:String, password:String): User?{

        viewModelScope.launch {
            user = repository.getUser(userName,password)!!
        }
        return user
    }

    //Insert user in database
    fun insert(user: User){
        repository.insertUser(user)
    }

    //RepositoryFactory class
    class LoginViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}