package com.ead.authuser.exceptions

import org.springframework.http.HttpStatus

enum class ErrorsEnum(val title: String, val detail: String) {
    NOT_FOUND_EXCEPTION(title = "Entity not found", detail = "Entity not found for informed id"),
    INTERNAL_SERVER_EXCEPTION("Internal server error", "Exception uncaught"),
    USERNAME_ALREADY_TAKEN_EXCEPTION("Username is already taken", "Username is already taken"),
    EMAIL_ALREADY_TAKEN_EXCEPTION("Email is already taken", "Email is already taken"),
    MISMATCHED_OLD_PASSWORD("Mismatched old password", "Mismatched old password"),
}