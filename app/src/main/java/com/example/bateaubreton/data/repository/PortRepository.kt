package com.example.bateaubreton.data.repository

import com.android.volley.VolleyError
import com.example.bateaubreton.data.dao.PortDao
import com.example.bateaubreton.data.entity.Port
import com.example.bateaubreton.data.network.GetPorts
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PortRepository private constructor(private val dao: PortDao) {
    private var _cache: MutableList<Port>? = null

    suspend fun getData() : MutableList<Port>
    {
        return _cache ?: getDataFromNetwork().also { _cache = it }
    }

    private suspend fun getDataFromNetwork() : MutableList<Port>
    {
        return suspendCoroutine { continuation ->
            GetPorts.execute(object : GetPorts.Result {
                override fun onSuccess(data: MutableList<Port>)
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

    suspend fun add(model: Port)
    {
        val data = getData()
        data.add(model)
        dao.insertAll(model)
    }

    suspend fun delete(model: Port)
    {
        val data = getData()
        data.remove(model)
        dao.deleteAll(model)
    }

    companion object
    {
        @Volatile
        private var instance: PortRepository? = null

        fun getInstance(dao: PortDao): PortRepository
        {
            return instance ?: synchronized(this) {
                instance ?: PortRepository(dao)
                    .also { instance = it }
            }
        }
    }
}
