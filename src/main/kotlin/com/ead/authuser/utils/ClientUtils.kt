package com.ead.authuser.utils

import org.springframework.data.domain.Pageable
import java.util.*

object ClientUtils {

    @JvmStatic
    fun createUrlGetAllCoursesByUser(userId: UUID, pageable: Pageable) =
        "/courses?userId=$userId" +
                "&page=${pageable.pageNumber}" +
                "&size=${pageable.pageSize}" +
                "&sort=${pageable.sort.toString().replace(": ", ",")}"

}