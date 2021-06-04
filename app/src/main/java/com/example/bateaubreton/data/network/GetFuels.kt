package com.example.bateaubreton.data.network

import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.example.bateaubreton.data.entity.Fuel
import com.example.bateaubreton.data.entity.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object GetFuels
{
    interface Result
    {
        fun onSuccess(data: MutableList<Fuel>)
        fun onFailure(error: VolleyError)
    }

    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private const val url: String = "https://www.teleobjet.fr/Ports/prix.php"

    fun execute(result: Result)
    {
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val data: MutableList<Fuel> = jsonFormat.decodeFromString(response.toString())
                result.onSuccess(data)
            },
            { error ->
                result.onFailure(error)
            }
        )
        NetworkApi.getInstance().addToRequestQueue(request)
    }
}