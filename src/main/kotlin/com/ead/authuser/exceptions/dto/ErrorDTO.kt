package com.ead.authuser.exceptions.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ErrorDTO(
    val status: Int = 0,
    val type: String? = "",
    val title: String? = "",
    val detail: String? = "")