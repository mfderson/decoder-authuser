package com.ead.authuser.services

import com.ead.authuser.dtos.UserDTO
import com.ead.authuser.models.UserModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.*

interface UserService {
    fun findAll(): List<UserModel>?

    fun findById(id: UUID): UserModel

    fun deleteById(id: UUID)

    fun save(user: UserModel): UserModel

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun updateUserData(id: UUID, userDTO: UserDTO): UserModel

    fun updatePassword(id: UUID, userDTO: UserDTO): UserModel

    fun updateImage(id: UUID, userDTO: UserDTO): UserModel

    fun registerUser(userDTO: UserDTO): UserModel

    fun findAll(spec: Specification<UserModel>, pageable: Pageable): Page<UserModel>

    fun updateUserType(userModel: UserModel): UserModel

    fun saveAndPublishUserEvent(userModel: UserModel): UserModel

    fun deleteAndPublishUserEvent(id: UUID)

    fun updateAndPublishUserEvent(userModel: UserModel): UserModel
}