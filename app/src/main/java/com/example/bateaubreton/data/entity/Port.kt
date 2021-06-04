package com.example.bateaubreton.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Port(
    val id: Int,
    @SerialName("nom") val name: String,
    @SerialName("lat") val latitude: Float,
    @SerialName("lon") val longitude: Float
)
