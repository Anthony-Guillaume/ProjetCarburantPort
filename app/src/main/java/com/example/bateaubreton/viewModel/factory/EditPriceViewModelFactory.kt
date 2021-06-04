package com.example.bateaubreton.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bateaubreton.data.repository.FuelRepository
import com.example.bateaubreton.viewModel.EditPriceViewModel

class EditPriceViewModelFactory(
    private val fuelRepository: FuelRepository
)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return EditPriceViewModel(fuelRepository) as T
    }
}