package com.ead.authuser.services.impl

import com.ead.authuser.dtos.UserDTO
import com.ead.authuser.enums.ActionType
import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.ead.authuser.exceptions.EmailAlreadyTakenException
import com.ead.authuser.exceptions.EntityNotFoundException
import com.ead.authuser.exceptions.MismatchedOldPasswordException
import com.ead.authuser.exceptions.UsernameAlreadyTakenException
import com.ead.authuser.models.UserModel
import com.ead.authuser.models.convertToUsereventDto
import com.ead.authuser.publishers.UserEventPublisher
import com.ead.authuser.repositories.UserRepository
import com.ead.authuser.services.UserService
import com.ead.authuser.utils.DateTimeUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserServiceImpl(
    val repository: UserRepository,
    val userEventPublisher: UserEventPublisher
): UserService {

    companion object {
        val LOGGER: Logger = LogManager.getLogger()
    }

    override fun findAll(): List<UserModel>? = repository.findAll()

    override fun findAll(spec: Specification<UserModel>, pageable: Pageable): Page<UserModel> {
        return repository.findAll(spec, pageable)
    }

    override fun findById(id: UUID): UserModel =
        repository.findById(id).orElseThrow { EntityNotFoundException("Entity with id: $id not found") }

    @Transactional
    override fun deleteById(id: UUID) {
        val user = findById(id)
        repository.delete(user)
    }

    override fun save(user: UserModel): UserModel {
        existsByUsername(user.username)
        existsByEmail(user.email)
        return repository.save(user)
    }

    override fun existsByUsername(username: String): Boolean {
        val alreadyExists = repository.existsByUsername(username)
        if (alreadyExists) {
            LOGGER.warn("Username is already taken: $username")
            throw UsernameAlreadyTakenException("Error: Username is already taken")
        }
        return alreadyExists
    }

    override fun existsByEmail(email: String): Boolean {
        val alreadyExists = repository.existsByEmail(email)
        if (alreadyExists) {
            LOGGER.warn("Email is already taken: $email")
            throw EmailAlreadyTakenException("Error: Email is already taken")
        }
        return alreadyExists
    }

    override fun updateUserData(id: UUID, userDTO: UserDTO): UserModel {
        val user = findById(id)
        user.apply {
            fullName = userDTO.fullName
            phoneNumber = userDTO.phoneNumber
            cpf = userDTO.cpf
            lastUpdateDate = DateTimeUtils.utcLocalDateTime()
        }

        val savedUser = repository.save(user)
        LOGGER.debug("PUT updateUserData userId saved: ${savedUser.id}")
        LOGGER.info("User updated successfully userId: ${savedUser.id}")
        return savedUser
    }

    override fun updatePassword(id: UUID, userDTO: UserDTO): UserModel {
        val user = findById(id)
        if (user.password != userDTO.oldPassword) {
            LOGGER.warn("Mismatched old password userId: $id")
            throw MismatchedOldPasswordException("Error: Mismatched old password")
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

        val savedUser = saveAndPublishUserEvent(user)
        LOGGER.debug("POST registerUser userId saved: ${savedUser.id}")
        LOGGER.info("User saved successfully userId: ${savedUser.id}")
        return savedUser
    }

    override fun updateUserType(userModel: UserModel) =
        repository.save(userModel)

    @Transactional
    override fun saveAndPublishUserEvent(userModel: UserModel): UserModel {
        val savedUserModel = repository.save(userModel)
        userEventPublisher.publishUserEvent(savedUserModel.convertToUsereventDto(), ActionType.CREATE)
        return savedUserModel
    }
}