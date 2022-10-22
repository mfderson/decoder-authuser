package com.ead.authuser.utils

import java.time.LocalDateTime
import java.time.ZoneId

object DateTimeUtils {

    @JvmStatic
    fun utcLocalDateTime() = LocalDateTime.now(ZoneId.of("UTC"))
}