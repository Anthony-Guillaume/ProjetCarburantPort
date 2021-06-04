package com.example.bateaubreton.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bateaubreton.data.repository.FuelRepository
import com.example.bateaubreton.data.repository.LocationRepository
import com.example.bateaubreton.data.repository.PortRepository
import com.example.bateaubreton.viewModel.PortViewModel

class PortViewModelFactory(
    private val fuelRepository: FuelRepository,
    private val portRepository: PortRepository,
    private val locationRepository: LocationRepository
)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return PortViewModel(fuelRepository, portRepository, locationRepository) as T
    }
}