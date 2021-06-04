package com.example.bateaubreton.viewModel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bateaubreton.data.entity.Fuel
import com.example.bateaubreton.data.repository.FuelRepository
import com.example.bateaubreton.data.repository.LocationRepository
import com.example.bateaubreton.data.repository.PortRepository
import com.example.bateaubreton.view.utils.PortLocation
import com.example.bateaubreton.view.utils.PortUtils
import kotlinx.coroutines.launch

class PortViewModel(
    private val fuelRepository: FuelRepository,
    private val portRepository: PortRepository,
    private val locationRepository: LocationRepository
)   : ViewModel()
{
    private val _fuels: MutableLiveData<List<Fuel>> = MutableLiveData(mutableListOf())
    val fuels get() = _fuels as LiveData<List<Fuel>>
    private val _portLocations: MutableLiveData<List<PortLocation>> = MutableLiveData(mutableListOf())
    val portLocations get() = _portLocations as LiveData<List<PortLocation>>
    private val _currentLocation: MutableLiveData<Location> = MutableLiveData()
    val currentLocation get() = _currentLocation as LiveData<Location>
    private val _distanceUnit: MutableLiveData<PortUtils.DistanceUnit> = MutableLiveData(PortUtils.DistanceUnit.Km)
    val distanceUnit get() = _distanceUnit as LiveData<PortUtils.DistanceUnit>
    private val _searchRadius: MutableLiveData<Int> = MutableLiveData(10)
    val searchRadius get() = _searchRadius as LiveData<Int>

    private suspend fun updatePortLocations()
    {
        _fuels.value = fuelRepository.getAll()
        _portLocations.value = portRepository.getData().map { port ->
            var distance: Double = PortUtils.computeDistanceInKm(port, _currentLocation.value!!)
            if (_distanceUnit.value == PortUtils.DistanceUnit.NauticalMiles)
            {
                distance = PortUtils.kmToNauticalMiles(distance)
            }
            val fuel: Fuel? = _fuels.value?.find { it.id == port.id }
            if (fuel == null)
            {
                PortLocation(port, distance, Fuel(port.id, 0.0, 0.0))
            }
            else
            {
                PortLocation(port, distance, fuel)
            }
        }.filter {
                it.distance < _searchRadius.value!!
            }.sortedBy { it.distance }
    }

    fun fetchData()
    {
        viewModelScope.launch {
            _currentLocation.value = locationRepository.getCurrentLocation()
            updatePortLocations()
        }
    }

    fun updateSearchRadius(value: String)
    {
        if (value.isNotEmpty() && _searchRadius.value != value.toInt())
        {
            viewModelScope.launch {
                _searchRadius.value = value.toInt()
                updatePortLocations()
            }
        }
    }

    fun updateLocation()
    {
        viewModelScope.launch {
            _currentLocation.value = locationRepository.getCurrentLocation()
            updatePortLocations()
        }
    }

    fun getFuel(index: Int) : Fuel
    {
        val portLocation: PortLocation = _portLocations.value!![index]
        return _fuels.value!!.find { it.id == portLocation.port.id }!!
    }

    fun updateDistanceUnit(index: Int)
    {
        val unit: PortUtils.DistanceUnit = PortUtils.DistanceUnit.values()[index]
        if (_distanceUnit.value != unit)
        {
            viewModelScope.launch {
                _distanceUnit.value = unit
                updatePortLocations()
            }
        }
    }
}