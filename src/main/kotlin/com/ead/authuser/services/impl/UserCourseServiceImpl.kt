package com.ead.authuser.services.impl

import com.ead.authuser.dtos.UserDTO
import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.ead.authuser.exceptions.EmailAlreadyTakenException
import com.ead.authuser.exceptions.EntityNotFoundException
import com.ead.authuser.exceptions.MismatchedOldPasswordException
import com.ead.authuser.exceptions.UsernameAlreadyTakenException
import com.ead.authuser.models.UserCourseModel
import com.ead.authuser.models.UserModel
import com.ead.authuser.repositories.UserCourseRepository
import com.ead.authuser.repositories.UserRepository
import com.ead.authuser.services.UserCourseService
import com.ead.authuser.services.UserService
import com.ead.authuser.utils.DateTimeUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserCourseServiceImpl(val repository: UserCourseRepository): UserCourseService {

    companion object {
        val LOGGER: Logger = LogManager.getLogger()
    }

    override fun existsByUserAndCourseId(user: UserModel, courseId: UUID) =
        repository.existsByUserAndCourseId(user, courseId)

    override fun save(userCourseModel: UserCourseModel): UserCourseModel {
        return repository.save(userCourseModel)
    }

    override fun existsByCourseId(courseId: UUID): Boolean {
        return repository.existsByCourseId(courseId)
    }

    @Transactional
    override fun deleteUserCourseByCourse(courseId: UUID) {
        repository.deleteAllByCourseId(courseId)
    }
}