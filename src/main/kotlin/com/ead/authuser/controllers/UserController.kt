package com.ead.authuser.controllers

import com.ead.authuser.dtos.UserDTO
import com.ead.authuser.dtos.views.UserDTOView
import com.ead.authuser.models.UserModel
import com.ead.authuser.services.UserService
import com.ead.authuser.services.impl.UserServiceImpl
import com.ead.authuser.specifications.SpecificationTemplate
import com.fasterxml.jackson.annotation.JsonView
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/users")
class UserController(val service: UserService) {

    companion object {
        val LOGGER: Logger = LogManager.getLogger()
    }

    @GetMapping
    fun getAllUsers(
        spec: SpecificationTemplate.UserSpec,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["id"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable): ResponseEntity<Page<UserModel>> {
        val userPage: Page<UserModel> = service.findAll(spec, pageable)
        userPage.toList().forEach {
            val selfLink = linkTo(UserController::class.java).slash(it.id).withSelfRel()
            it.add(selfLink)
        }

        return ResponseEntity.status(HttpStatus.OK).body(userPage)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: UUID): ResponseEntity<*> {
        LOGGER.debug("DELETE deleteById userId: $id")
        service.deleteById(id)
        LOGGER.info("User deleted successfully userId: $id")
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully")
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @RequestBody @Validated(UserDTOView.UserPut::class) @JsonView(UserDTOView.UserPut::class) userDTO: UserDTO
    ): UserModel {
        LOGGER.debug("PUT updateUser userDto: $userDTO")
        val updatedUser = service.updateUserData(id, userDTO)
        LOGGER.debug("PUT updateUser userId: $id")
        LOGGER.info("User updated successfully userId: $id")
        return updatedUser
    }

    @PutMapping("/{id}/password")
    fun updatePassword(
        @PathVariable id: UUID,
        @RequestBody @Validated(UserDTOView.PasswordPut::class) @JsonView(UserDTOView.PasswordPut::class) userDTO: UserDTO
    ): ResponseEntity<*> {
        LOGGER.debug("PUT updatePassword userDto: $userDTO")
        val updatedUser = service.updatePassword(id, userDTO)
        LOGGER.debug("PUT updatePassword userId: $id")
        LOGGER.info("Password updated successfully userId: $id")
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully")
    }

    @PutMapping("/{id}/image")
    fun updateImage(
        @PathVariable id: UUID,
        @RequestBody @Validated(UserDTOView.ImagePut::class) @JsonView(UserDTOView.ImagePut::class) userDTO: UserDTO
    ): UserModel {
        LOGGER.debug("PUT updateImage userDto: $userDTO")
        val savedUser = service.updateImage(id, userDTO)
        LOGGER.debug("PUT updateImage userId saved $id ")
        LOGGER.info("Image updated successfully userId: $id ")
        return savedUser
    }
}