package com.ead.authuser.validations

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UsernameConstraintImpl: ConstraintValidator<UsernameContraint, String> {

    override fun isValid(username: String?, context: ConstraintValidatorContext?): Boolean {
        if (username == null || username.trim().isEmpty() || username.contains(" "))
            return false
        return true
    }

}
