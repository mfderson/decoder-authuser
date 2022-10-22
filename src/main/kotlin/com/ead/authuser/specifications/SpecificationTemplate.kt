package com.ead.authuser.specifications

import com.ead.authuser.models.UserModel
import net.kaczmarzyk.spring.data.jpa.domain.In
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification

class SpecificationTemplate {

    @And(
        Spec(path = "type", spec = In::class, paramSeparator = ',', defaultVal = arrayOf("ADMIN", "STUDENT", "INSTRUCTOR")),
        Spec(path = "status", spec = In::class, paramSeparator = ',', defaultVal = arrayOf("ACTIVE", "BLOCKED")),
        Spec(path = "email", spec = Like::class)
    )
    interface UserSpec: Specification<UserModel> {}
}