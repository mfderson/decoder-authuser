package com.ead.authuser.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class MismatchedOldPasswordException(message: String?) : RuntimeException(message) {
}