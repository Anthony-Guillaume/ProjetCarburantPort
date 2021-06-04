package com.example.bateaubreton.data.repository

import android.location.Location
import com.example.bateaubreton.view.utils.FusedLocationHelper
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationRepository private constructor(private val fusedLocationHelper: FusedLocationHelper)
{
    private var _cache: Location? = null

    private suspend fun getData() : Location
    {
        return _cache ?: requestData().also { _cache = it }
    }

    private suspend fun requestData() : Location
    {
        return suspendCoroutine { continuation ->
            fusedLocationHelper.requestLastLocation { continuation.resume(it) }
        }
    }

    suspend fun getCurrentLocation() : Location
    {
        return getData()
    }

    companion object
    {
        @Volatile
        private var instance: LocationRepository? = null

        fun getInstance(fusedLocationHelper: FusedLocationHelper): LocationRepository
        {
            return instance ?: synchronized(this) {
                instance ?: LocationRepository(fusedLocationHelper).also { instance = it }
            }
        }
    }
}
