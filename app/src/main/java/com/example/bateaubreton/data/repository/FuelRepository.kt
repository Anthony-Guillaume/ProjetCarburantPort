package com.example.bateaubreton.data.repository

import android.util.Log
import com.android.volley.VolleyError
import com.example.bateaubreton.data.dao.FuelDao
import com.example.bateaubreton.data.entity.Fuel
import com.example.bateaubreton.data.network.ChangeFuel
import com.example.bateaubreton.data.network.GetFuels
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FuelRepository private constructor(private val dao: FuelDao) {
    private var _cache: MutableList<Fuel>? = null

    private suspend fun getData(): MutableList<Fuel> {
        return _cache ?: getDataFromNetwork().also { _cache = it }
    }

    private suspend fun getDataFromNetwork() : MutableList<Fuel>
    {
        return suspendCoroutine { continuation ->
            GetFuels.execute(object : GetFuels.Result {
                override fun onSuccess(data: MutableList<Fuel>)
                {
                    _cache = data
                    continuation.resume(data)
                }

                override fun onFailure(error: VolleyError)
                {
                    continuation.resume(mutableListOf())
                }
            })
        }
    }

    suspend fun change(fuel: Fuel)
    {
        ChangeFuel.execute(object : ChangeFuel.Result {
            override fun onSuccess(data: Int)
            {
            }

            override fun onFailure(error: VolleyError)
            {
            }
        }, fuel)
    }

    suspend fun getAll(): MutableList<Fuel>
    {
        return getData()
    }

    companion object {
        @Volatile
        private var instance: FuelRepository? = null

        fun getInstance(dao: FuelDao): FuelRepository
        {
            return instance ?: synchronized(this) {
                instance ?: FuelRepository(dao).also { instance = it }
            }
        }
    }
}