package com.example.bateaubreton.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class FuelType { sp98, Diesel}

@Serializable
data class Fuel(
    @SerialName("idport") val id: Int,
    @SerialName("gazole") var diesel: Double,
    var sp98: Double)