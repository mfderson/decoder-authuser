package com.ead.authuser.dtos

import org.jetbrains.annotations.NotNull

data class JwtDto(
    @field:NotNull
    val token: String,
    val type: String = "Bearer"
)