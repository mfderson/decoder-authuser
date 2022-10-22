package com.ead.authuser.controllers

import com.ead.authuser.dtos.UserDTO
import com.ead.authuser.dtos.views.UserDTOView
import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.ead.authuser.models.UserModel
import com.ead.authuser.services.UserService
import com.ead.authuser.utils.DateTimeUtils
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.beans.BeanUtils
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/auth")
class AuthenticationController(val service: UserService) {

    @PostMapping("/signup")
    fun registerUser(
        @RequestBody @Validated(UserDTOView.RegistrationPost::class) @JsonView(UserDTOView.RegistrationPost::class) userDTO: UserDTO
    ): UserModel {
        val user = UserModel(
            status = UserStatus.ACTIVE,
            type = UserType.STUDENT,
            creationDate = DateTimeUtils.utcLocalDateTime(),
            lastUpdateDate = DateTimeUtils.utcLocalDateTime()
        )

        BeanUtils.copyProperties(userDTO, user)

        return service.save(user)
    }
}