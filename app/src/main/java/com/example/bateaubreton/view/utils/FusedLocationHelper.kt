package com.example.bateaubreton.view.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import com.google.android.gms.location.*

class FusedLocationHelper(private val fusedLocationClient: FusedLocationProviderClient)
{
    @SuppressLint("MissingPermission")
    fun requestLastLocation(activity: Activity, onLocationFound: (Location) -> Unit)
    {
        if (CheckHelper.areGpsPermissionEnabled(activity))
        {
            if (CheckHelper.isLocationEnabled(activity))
            {
                fusedLocationClient.lastLocation
                    .addOnCompleteListener { task ->
                        val location: Location? = task.result
                        if (location == null)
                        {
                            requestNewLocationData(onLocationFound)
                        }
                        else
                        {
                            onLocationFound.invoke(location)
                        }
                    }
            }
            else
            {
                Toast.makeText(activity, "Please turn on" + " your location...", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
        }
        else
        {
            CheckHelper.requestGpsPermissions(activity)
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLastLocation(onLocationFound: (Location) -> Unit)
    {
        fusedLocationClient.lastLocation
            .addOnCompleteListener { task ->
                val location: Location? = task.result
                if (location == null)
                {
                    requestNewLocationData(onLocationFound)
                }
                else
                {
                    onLocationFound.invoke(location)
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(onLocationResult: (Location) -> Unit)
    {
        val locationRequest = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 0
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }
        val locationCallback: LocationCallback = object : LocationCallback()
        {
            override fun onLocationResult(locationResult: LocationResult)
            {
                val location: Location = locationResult.lastLocation
                onLocationResult.invoke(location)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()!!
        )
    }
}