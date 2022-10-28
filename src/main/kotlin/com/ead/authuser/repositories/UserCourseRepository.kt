package com.ead.authuser.repositories

import com.ead.authuser.models.UserCourseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserCourseRepository : JpaRepository<UserCourseModel, UUID> {
}