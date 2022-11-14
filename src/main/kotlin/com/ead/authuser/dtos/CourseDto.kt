package com.ead.authuser.dtos

import com.ead.course.enums.CourseLevel
import com.ead.course.enums.CourseStatus
import java.util.*

data class CourseDto(
    val courseId: UUID = UUID.randomUUID(),
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val courseStatus: CourseStatus = CourseStatus.INPROGRESS,
    val userIntructor: UUID = UUID.randomUUID(),
    val courseLevel: CourseLevel = CourseLevel.BEGINNER
)