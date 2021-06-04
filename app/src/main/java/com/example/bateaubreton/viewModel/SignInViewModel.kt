package com.example.bateaubreton.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bateaubreton.data.entity.User
import com.example.bateaubreton.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: UserRepository) : ViewModel()
{
    private val _localPseudo: MutableLiveData<String> = MutableLiveData()
    val localPseudo get() = _localPseudo as LiveData<String>
    private val _localPassword: MutableLiveData<String> = MutableLiveData()
    val localPassword get() = _localPassword as LiveData<String>

    private var inputPseudo: String? = null
    private var inputPassword: String? = null

    private val _rememberMe: MutableLiveData<Boolean> = MutableLiveData(false)
    val rememberMe get() = _rememberMe as LiveData<Boolean>
    private val _userValid: MutableLiveData<Boolean> = MutableLiveData()
    val userValid get() = _userValid as LiveData<Boolean>

    fun fetchData()
    {
        viewModelScope.launch {
            _rememberMe.value = userRepository.getRememberMe()
            if (_rememberMe.value == true)
            {
                userRepository.getLocalUser()?.let {
                    _localPseudo.value = it.pseudo
                    _localPassword.value = it.password
                }
            }
        }
    }

    fun updatePseudo(value : String)
    {
        inputPseudo = value
    }

    fun updatePassword(value : String)
    {
        inputPassword = value
    }

    fun updateRememberMe(value: Boolean)
    {
        _rememberMe.value = value
    }

    fun signIn()
    {
        if (inputPseudo != null && inputPassword != null)
        {
            viewModelScope.launch {
                _userValid.value = userRepository.isUserValid(inputPseudo!!, inputPassword!!)
                if (_userValid.value == true)
                {
                    viewModelScope.launch {
                        userRepository.updateRememberMe(_rememberMe.value!!)
                        userRepository.addLocalUser(User("", inputPseudo!!, inputPassword!!))
                    }
                }
            }
        }
        else
        {
            _userValid.value = false
        }
    }
}