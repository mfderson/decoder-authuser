package com.ead.authuser.controllers

import com.ead.authuser.dtos.UserDTO
import com.ead.authuser.dtos.views.UserDTOView
import com.ead.authuser.models.UserModel
import com.ead.authuser.services.RoleService
import com.ead.authuser.services.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/auth")
class AuthenticationController(
    val service: UserService
) {

    companion object {
        val LOGGER: Logger = LogManager.getLogger()
    }

    @PostMapping("/signup")
    fun registerUser(
        @RequestBody @Validated(UserDTOView.RegistrationPost::class) @JsonView(UserDTOView.RegistrationPost::class) userDTO: UserDTO
    ): UserModel {
        LOGGER.debug("POST registerUser userDto received: $userDTO")
        return service.registerUser(userDTO)
    }
}