package com.example.bateaubreton.view.utils

import android.location.Location
import com.example.bateaubreton.data.entity.Port

object PortUtils
{
    enum class DistanceUnit { Km, NauticalMiles }

    fun computeDistanceInKm(port: Port, location: Location) : Double
    {
        val results = FloatArray(1)
        Location.distanceBetween( location.latitude, location.longitude,
            port.latitude.toDouble(), port.longitude.toDouble(), results)
        return results.first() * 0.001
    }

    fun kmToNauticalMiles(value: Double) : Double
    {
        return value / 1.852
    }

    fun nauticalMilesToKm(value: Double) : Double
    {
        return value * 1.852
    }
}