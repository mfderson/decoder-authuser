package com.ead.authuser.services

import com.ead.authuser.models.UserCourseModel
import com.ead.authuser.models.UserModel
import java.util.UUID

interface UserCourseService {

    fun existsByUserAndCourseId(user: UserModel, courseId: UUID): Boolean
    fun save(userCourseModel: UserCourseModel): UserCourseModel
}