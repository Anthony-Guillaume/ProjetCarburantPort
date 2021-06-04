package com.example.bateaubreton.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bateaubreton.viewModel.AuthenticationViewModel

class AuthenticationViewModelFactory
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return AuthenticationViewModel() as T
    }
}