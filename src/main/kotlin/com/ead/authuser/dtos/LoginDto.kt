package com.ead.authuser.dtos

import javax.validation.constraints.NotBlank

data class LoginDto(
    @field:NotBlank
    val login: String,

    @field:NotBlank
    val password: String
)