package com.ead.authuser.controllers

import com.ead.authuser.dtos.InstructorDto
import com.ead.authuser.enums.UserType
import com.ead.authuser.services.UserService
import com.ead.authuser.utils.DateTimeUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/instructors")
class InstructorController(
    val userService: UserService
) {

    @PostMapping("/subscription")
    fun saveSubscriptionInstructor(@RequestBody @Valid instructorDto: InstructorDto): ResponseEntity<*> {
        val user = userService.findById(instructorDto.userId)

        user.type = UserType.INSTRUCTOR
        user.lastUpdateDate = DateTimeUtils.utcLocalDateTime()
        userService.updateAndPublishUserEvent(user)

        return ResponseEntity.status(HttpStatus.OK).body(user)
    }
}