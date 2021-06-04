package com.example.bateaubreton.data.network

import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.bateaubreton.data.entity.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

object AddUser
{
    interface Result
    {
        fun onSuccess(data: Int)
        fun onFailure(error: VolleyError)
    }

    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private const val url: String = "https://www.teleobjet.fr/Ports/user.php"

    fun execute(result: Result, user: User)
    {
        val params: String = jsonFormat.encodeToString(user)
        val request = JsonObjectRequest(
            Request.Method.PUT,
            url,
            JSONObject(params),
            { response ->
//                val data: Int = response.toString().toInt()
//                result.onSuccess(data)
            },
            { error ->
                result.onFailure(error)
            }
        )
        NetworkApi.getInstance().addToRequestQueue(request)
    }
}