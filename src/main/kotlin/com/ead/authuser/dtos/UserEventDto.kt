package com.ead.authuser.dtos

import java.util.UUID

data class UserEventDto(
    val userId: UUID,
    val username: String = "",
    val email: String = "",
    val fullName: String = "",
    val userStatus: String = "",
    val userType: String = "",
    val phoneNumber: String = "",
    val cpf: String = "",
    val imageUrl: String = "",
    var actionType: String = ""
)
