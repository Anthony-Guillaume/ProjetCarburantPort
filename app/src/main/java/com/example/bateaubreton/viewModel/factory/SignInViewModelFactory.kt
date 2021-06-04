package com.example.bateaubreton.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bateaubreton.data.repository.UserRepository
import com.example.bateaubreton.viewModel.SignInViewModel

class SignInViewModelFactory(private val repository: UserRepository)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return SignInViewModel(repository) as T
    }
}