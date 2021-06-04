package com.example.bateaubreton.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bateaubreton.data.entity.Fuel
import com.example.bateaubreton.data.entity.FuelType
import com.example.bateaubreton.data.repository.FuelRepository
import kotlinx.coroutines.launch

class EditPriceViewModel(private val fuelRepository: FuelRepository) : ViewModel()
{
    private var price : String? = null
    private var fuel: Fuel? = null
    private var type: FuelType = FuelType.sp98

    private val _canValidate: MutableLiveData<Boolean> = MutableLiveData(false)
    val canValidate get() = _canValidate as LiveData<Boolean>

    fun initFuel(newFuel: Fuel)
    {
        fuel = newFuel
    }

    fun validatePrice()
    {
        assert(_canValidate.value == true)
        val fuel = fuel ?: return
        if (type == FuelType.sp98)
        {
            fuel.sp98 = price!!.toDouble()
        }
        else
        {
            fuel.diesel = price!!.toDouble()
        }
        viewModelScope.launch {
            fuelRepository.change(fuel)
        }
    }

    fun updatePrice(newPrice: String)
    {
        _canValidate.value = newPrice.isNotEmpty()
        price = newPrice
    }

    fun updateType(index: Int)
    {
        type = FuelType.values()[index]
    }
}