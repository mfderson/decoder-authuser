package com.ead.authuser.controllers

import com.ead.authuser.configs.security.JwtProvider
import com.ead.authuser.dtos.JwtDto
import com.ead.authuser.dtos.LoginDto
import com.ead.authuser.dtos.UserDTO
import com.ead.authuser.dtos.views.UserDTOView
import com.ead.authuser.models.UserModel
import com.ead.authuser.services.RoleService
import com.ead.authuser.services.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/auth")
class AuthenticationController(
    val service: UserService,
    val jwtProvider: JwtProvider,
    val authenticationManager: AuthenticationManager
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

    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody request: LoginDto): ResponseEntity<JwtDto> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.login, request.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtProvider.generateJwt(authentication)

        return ResponseEntity.ok(JwtDto(jwt))
    }
}