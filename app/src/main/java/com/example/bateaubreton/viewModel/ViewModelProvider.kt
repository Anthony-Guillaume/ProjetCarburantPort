package com.example.bateaubreton.viewModel

import com.example.bateaubreton.MyApplication
import com.example.bateaubreton.data.Database
import com.example.bateaubreton.data.repository.FuelRepository
import com.example.bateaubreton.data.repository.LocationRepository
import com.example.bateaubreton.data.repository.PortRepository
import com.example.bateaubreton.data.repository.UserRepository
import com.example.bateaubreton.view.utils.FusedLocationHelper
import com.example.bateaubreton.viewModel.factory.*
import com.google.android.gms.location.LocationServices

object ViewModelProvider
{
    fun provideAuthenticationViewModelFactory() : AuthenticationViewModelFactory
    {
        return AuthenticationViewModelFactory()
    }

    fun provideSignUpViewModelFactory() : SignUpViewModelFactory
    {
        val userRepository = UserRepository.getInstance(Database.getInstance().userDao)
        return SignUpViewModelFactory(userRepository)
    }

    fun provideSignInViewModelFactory() : SignInViewModelFactory
    {
        val userRepository = UserRepository.getInstance(Database.getInstance().userDao)
        return SignInViewModelFactory(userRepository)
    }

    fun provideFuelInformationViewModelFactory() : PortViewModelFactory
    {
        val fuelRepository = FuelRepository.getInstance(Database.getInstance().fuelDao)
        val portRepository = PortRepository.getInstance(Database.getInstance().portDao)
        val locationRepository = LocationRepository.getInstance(
            FusedLocationHelper(LocationServices.getFusedLocationProviderClient(MyApplication.applicationContext)))
        return PortViewModelFactory(fuelRepository, portRepository, locationRepository)
    }

    fun provideEditPriceViewModelFactory() : EditPriceViewModelFactory
    {
        val fuelRepository = FuelRepository.getInstance(Database.getInstance().fuelDao)
        return EditPriceViewModelFactory(fuelRepository)
    }
}