package com.example.bateaubreton.data.network

import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.example.bateaubreton.data.entity.Port
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object GetPorts
{
    interface Result
    {
        fun onSuccess(data: MutableList<Port>)
        fun onFailure(error: VolleyError)
    }

    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private const val url: String = "https://www.teleobjet.fr/Ports/port.php"

    fun execute(result: Result)
    {
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val data: MutableList<Port> = jsonFormat.decodeFromString(response.toString())
                result.onSuccess(data)
            },
            { error ->
                result.onFailure(error)
            }
        )
        NetworkApi.getInstance().addToRequestQueue(request)
    }
}