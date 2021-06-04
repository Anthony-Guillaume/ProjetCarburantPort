package com.example.bateaubreton.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bateaubreton.data.repository.UserRepository
import com.example.bateaubreton.viewModel.SignUpViewModel

class SignUpViewModelFactory(private val repository: UserRepository)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return SignUpViewModel(repository) as T
    }
}