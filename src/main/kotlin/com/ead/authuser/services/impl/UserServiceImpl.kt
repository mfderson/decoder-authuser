package com.ead.authuser.services.impl

import com.ead.authuser.dtos.UserDTO
import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.ead.authuser.exceptions.EmailAlreadyTakenException
import com.ead.authuser.exceptions.EntityNotFoundException
import com.ead.authuser.exceptions.MismatchedOldPasswordException
import com.ead.authuser.exceptions.UsernameAlreadyTakenException
import com.ead.authuser.models.UserModel
import com.ead.authuser.repositories.UserRepository
import com.ead.authuser.services.UserService
import com.ead.authuser.utils.DateTimeUtils
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(val repository: UserRepository): UserService {

    override fun findAll(): List<UserModel>? = repository.findAll()

    override fun findAll(spec: Specification<UserModel>, pageable: Pageable): Page<UserModel> {
        return repository.findAll(spec, pageable)
    }

    override fun findById(id: UUID): UserModel =
        repository.findById(id).orElseThrow { EntityNotFoundException("Entity with id: $id not found") }

    override fun deleteById(id: UUID) = repository.deleteById(id)

    override fun save(user: UserModel): UserModel {
        existsByUsername(user.username)
        existsByEmail(user.email)
        return repository.save(user)
    }

    override fun existsByUsername(username: String): Boolean {
        val alreadyExists = repository.existsByUsername(username)
        if (alreadyExists) {
            throw UsernameAlreadyTakenException("Username already taken")
        }
        return alreadyExists
    }

    override fun existsByEmail(email: String): Boolean {
        val alreadyExists = repository.existsByEmail(email)
        if (alreadyExists) {
            throw EmailAlreadyTakenException("Username already taken")
        }
        return alreadyExists
    }

    override fun updateUserData(id: UUID, userDTO: UserDTO): UserModel {
        val user = findById(id)
        user.fullName = userDTO.fullName
        user.phoneNumber = userDTO.phoneNumber
        user.cpf = userDTO.cpf
        user.lastUpdateDate = DateTimeUtils.utcLocalDateTime()

        return repository.save(user)
    }

    override fun updatePassword(id: UUID, userDTO: UserDTO): UserModel {
        val user = findById(id)
        if (user.password != userDTO.oldPassword) {
            throw MismatchedOldPasswordException("Mismatched old password")
        }
        user.password = userDTO.password
        user.lastUpdateDate = DateTimeUtils.utcLocalDateTime()

        return repository.save(user)
    }

    override fun updateImage(id: UUID, userDTO: UserDTO): UserModel {
        val user = findById(id)
        user.imageUrl = userDTO.imageUrl
        user.lastUpdateDate = DateTimeUtils.utcLocalDateTime()

        return repository.save(user)
    }

    override fun registerUser(userDTO: UserDTO): UserModel {
        existsByEmail(userDTO.email)
        existsByUsername(userDTO.username)

        val user = UserModel(
            status = UserStatus.ACTIVE,
            type = UserType.STUDENT,
            creationDate = DateTimeUtils.utcLocalDateTime(),
            lastUpdateDate = DateTimeUtils.utcLocalDateTime()
        )

        BeanUtils.copyProperties(userDTO, user)

        return repository.save(user)
    }
}