package com.ead.authuser.validations

import com.ead.authuser.dtos.views.UserDTOView
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UsernameConstraintImpl::class])
annotation class UsernameContraint(
    val message: String = "Invalid username",
    val groups: Array<KClass<UserDTOView.RegistrationPost>> = [],
    val payload: Array<KClass<Payload>> = []
)
