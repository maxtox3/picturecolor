package com.picturecolor.client.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationResponse(val token: String)

@Serializable
data class AuthorizationRequest(val name: String, val secondName: String)

@Serializable
data class User(
        val id: Int,
        val name: String,
        val secondName: String
)