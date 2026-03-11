package com.example.miniapp.model

import java.io.Serializable

data class Room(
    val id: String,
    var name: String,
    var price: Double,
    var isRented: Boolean = false,
    var tenantName: String? = null,
    var tenantPhone: String? = null
) : Serializable
