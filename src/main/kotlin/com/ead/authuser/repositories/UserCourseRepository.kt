package com.ead.authuser.repositories

import com.ead.authuser.models.UserCourseModel
import com.ead.authuser.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserCourseRepository : JpaRepository<UserCourseModel, UUID> {

    fun existsByUserAndCourseId(user: UserModel, courseId: UUID): Boolean

    @Query(value = "select * from users_courses where user_id = :userId", nativeQuery = true)
    fun findAllUserCourseIntoUser(userId: UUID): List<UserCourseModel>
}