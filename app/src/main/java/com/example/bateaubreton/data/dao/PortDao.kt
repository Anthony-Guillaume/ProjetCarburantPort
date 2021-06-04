package com.example.bateaubreton.data.dao

import com.example.bateaubreton.data.entity.Port
import kotlinx.serialization.json.Json

class PortDao
{
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    suspend fun insertAll(vararg entities: Port)
    {

    }

    suspend fun deleteAll(vararg entities: Port)
    {

    }

    suspend fun getAll() : MutableList<Port>
    {
        return mutableListOf()
    }
}