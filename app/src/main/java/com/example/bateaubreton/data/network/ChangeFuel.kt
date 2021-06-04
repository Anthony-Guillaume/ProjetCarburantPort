package com.example.bateaubreton.data.network

import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.bateaubreton.data.entity.Fuel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

object ChangeFuel
{
    interface Result
    {
        fun onSuccess(data: Int)
        fun onFailure(error: VolleyError)
    }

    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private const val url: String = "https://www.teleobjet.fr/Ports/prix.php"

    fun execute(result: Result, fuel: Fuel)
    {
        val params: String = jsonFormat.encodeToString(fuel)
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