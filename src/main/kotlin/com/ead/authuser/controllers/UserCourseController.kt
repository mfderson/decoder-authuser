package com.ead.authuser.controllers

import com.ead.authuser.clients.CourseClient
import com.ead.authuser.dtos.CourseDto
import com.ead.authuser.dtos.UserCourseDto
import com.ead.authuser.services.UserCourseService
import com.ead.authuser.services.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpStatusCodeException
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class UserCourseController(
    val courseClient: CourseClient,
    val userService: UserService,
    val userCourseService: UserCourseService
) {

    @GetMapping("/users/{userId}/courses")
    fun getAllCoursesByUser(
        @PageableDefault(page = 0, size = 10, sort = ["id"], direction = Sort.Direction.ASC) pageable: Pageable,
        @PathVariable(required = true) userId: UUID
    ): ResponseEntity<Page<CourseDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable))
    }

    @PostMapping("/users/{userId}/courses/subscription")
    fun saveSubscriptionCourseInUser(
        @PathVariable(required = true) userId: UUID,
        @RequestBody @Valid userCourseDto: UserCourseDto
    ): ResponseEntity<*> {
        val user = userService.findById(userId)

        if (userCourseService.existsByUserAndCourseId(user, userCourseDto.courseId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists!")
        }

//        try {
//            val courseResponse = courseClient.getCourseById(userCourseDto.courseId)
//            courseResponse.body?.let {
//
//            }
//        } catch (e: HttpStatusCodeException) {
//            if (e.statusCode.equals(HttpStatus.NOT_FOUND))
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found")
//        }

        val savedUserCourse = userCourseService.save(user.convertToUserCourseModel(userCourseDto.courseId))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserCourse)
    }
}