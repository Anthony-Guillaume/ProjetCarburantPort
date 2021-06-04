package com.example.bateaubreton.data.dao

import com.example.bateaubreton.data.entity.Fuel

class FuelDao
{
    suspend fun insertAll(vararg entities: Fuel)
    {

    }

    suspend fun deleteAll(vararg entities: Fuel)
    {

    }

    suspend fun getAll() : MutableList<Fuel>
    {
        return mutableListOf()
    }
}