package com.ead.authuser.specifications

import com.ead.authuser.models.UserCourseModel
import com.ead.authuser.models.UserModel
import net.kaczmarzyk.spring.data.jpa.domain.In
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification
import java.util.*

class SpecificationTemplate {

    companion object {
        fun userCourseId(courseId: UUID): Specification<UserModel> {
            return Specification<UserModel> { root, query, criteriaBuilder ->
                query.distinct(true)
                val userProd = root.join<UserModel, UserCourseModel>("usersCourses")
                return@Specification criteriaBuilder.equal(userProd.get<UUID>("courseId"), courseId)
            }
        }
    }

    @And(
        Spec(path = "type", spec = In::class, paramSeparator = ',', defaultVal = arrayOf("ADMIN", "STUDENT", "INSTRUCTOR")),
        Spec(path = "status", spec = In::class, paramSeparator = ',', defaultVal = arrayOf("ACTIVE", "BLOCKED")),
        Spec(path = "email", spec = Like::class),
        Spec(path = "fullName", spec = Like::class)
    )
    interface UserSpec: Specification<UserModel> {}
}