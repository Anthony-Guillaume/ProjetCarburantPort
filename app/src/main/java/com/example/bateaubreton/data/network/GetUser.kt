package com.example.bateaubreton.data.network

import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.bateaubreton.data.entity.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object GetUser
{
    interface Result
    {
        fun onSuccess(data: User)
        fun onFailure(error: VolleyError)
    }

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    fun execute(result: Result, pseudo: String, password: String)
    {
        val request = JsonObjectRequest(
            Request.Method.GET,
            "https://www.teleobjet.fr/Ports/user.php?pseudo=$pseudo&password=$password",
            null,
            { response ->
                val data: User = jsonFormat.decodeFromString(response.toString())
                result.onSuccess(data)
            },
            { error ->
                result.onFailure(error)
            }
        )
        NetworkApi.getInstance().addToRequestQueue(request)
    }
}