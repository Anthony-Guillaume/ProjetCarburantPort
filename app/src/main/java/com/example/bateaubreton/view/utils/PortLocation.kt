package com.example.bateaubreton.view.utils

import com.example.bateaubreton.data.entity.Fuel
import com.example.bateaubreton.data.entity.Port

data class PortLocation(
    val port: Port,
    var distance: Double,
    val fuel: Fuel
)
