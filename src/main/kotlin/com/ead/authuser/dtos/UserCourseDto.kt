package com.ead.authuser.dtos

import java.util.UUID
import javax.validation.constraints.NotNull

data class UserCourseDto(
    val userId: UUID,

    @field:NotNull
    val courseId: UUID
)
