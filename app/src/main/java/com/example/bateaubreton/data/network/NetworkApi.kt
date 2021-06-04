package com.example.bateaubreton.data.network

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.bateaubreton.MyApplication

class NetworkApi private constructor()
{

    private val networkRequestQueue: RequestQueue by lazy { Volley.newRequestQueue(MyApplication.applicationContext) }

    fun <T> addToRequestQueue(req: Request<T>)
    {
        networkRequestQueue.add(req)
    }

    companion object
    {
        @Volatile private var instance: NetworkApi? = null

        fun getInstance() : NetworkApi
        {
            return instance ?: synchronized(this) {
                instance ?: NetworkApi().also { instance = it }
            }
        }
    }
}
