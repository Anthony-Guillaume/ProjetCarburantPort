package com.example.bateaubreton.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bateaubreton.data.entity.User
import com.example.bateaubreton.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel()
{
    private var email : String? = null
    private var pseudo : String? = null
    private var password : String? = null
    private val _userValid: MutableLiveData<Boolean> = MutableLiveData()
    val userValid get() = _userValid as LiveData<Boolean>

    fun updateEmail(newEmail : String)
    {
        email = newEmail
    }

    fun updatePseudo(newPseudo : String)
    {
        pseudo = newPseudo
    }

    fun updatePassword(newPassword : String)
    {
        password = newPassword
    }

    fun signUp()
    {
        if (inputIsValid())
        {
            val user = User(email!!, pseudo!!, password!!)
            viewModelScope.launch {
                _userValid.value = userRepository.addUser(user)
            }
        }
        else
        {
            _userValid.value = false
        }
    }

    private fun inputIsValid() : Boolean
    {
        return email != null && pseudo != null && password != null
    }
}