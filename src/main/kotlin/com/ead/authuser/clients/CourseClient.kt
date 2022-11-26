package com.ead.authuser.clients

import com.ead.authuser.dtos.CourseDto
import com.ead.authuser.dtos.ResponsePageDto
import com.ead.authuser.utils.ClientUtils
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.util.UUID


@Component
class CourseClient(val restTemplate: RestTemplate) {

    companion object {
        val LOGGER = LogManager.getLogger()
    }

    @Value("\${ead.api.url.course}")
    lateinit var COURSE_REQUEST_URI: String

    fun getAllCoursesByUser(userId: UUID, pageable: Pageable): ResponsePageDto<CourseDto>? {
        var result: ResponseEntity<ResponsePageDto<CourseDto>>? = null
        val url = COURSE_REQUEST_URI + ClientUtils.createUrlGetAllCoursesByUser(userId, pageable)

        LOGGER.debug("Request URL: $url")
        LOGGER.info("Request URL: $url")

        try {
            result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {}
            )
            val searchResult = result.body?.content ?: mutableListOf()
            LOGGER.debug("Response number of elements: ${searchResult.size}")
        } catch (e: HttpStatusCodeException) {
            LOGGER.error("Error request $userId/courses ", e)
        }
        LOGGER.info("Ending request to $userId/courses")
        return result?.body
    }

    fun getCourseById(courseId: UUID): ResponseEntity<CourseDto> {
        val url = "$COURSE_REQUEST_URI/courses/$courseId"
        return restTemplate.exchange(url, HttpMethod.GET, null, CourseDto::class.java)
    }

    fun deleteUserInCourse(id: UUID) {
        val url = "$COURSE_REQUEST_URI/courses/users/$id"
        restTemplate.exchange(url, HttpMethod.DELETE, null, String::class.java)

    }
}