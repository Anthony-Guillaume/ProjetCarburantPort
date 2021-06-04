package com.example.bateaubreton.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("mailadr") val email: String,
    @SerialName("pseudo") val pseudo: String,
    @SerialName("pass") val password: String)