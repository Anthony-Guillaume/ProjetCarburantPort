package com.example.bateaubreton.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthenticationViewModel: ViewModel()
{
    private val _canSign: MutableLiveData<Boolean> = MutableLiveData()
    val canSign get() = _canSign as LiveData<Boolean>

    fun updateCanSign(value: Boolean)
    {
        _canSign.value = value
    }
}