package com.picturecolor.backend.model

import io.ktor.auth.*

data class User(
        val id: Int,
        val name: String,
        val secondName: String
) : Principal