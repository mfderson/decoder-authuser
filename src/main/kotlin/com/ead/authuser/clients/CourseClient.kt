package com.ead.authuser.clients

import com.ead.authuser.dtos.CourseDto
import com.ead.authuser.dtos.ResponsePageDto
import com.ead.authuser.utils.ClientUtils
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.PageImpl
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

//    @Retry(name = "retryInstance", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "circuitbreakerInstance"/*, fallbackMethod = "circuitbreakerFallback"*/)
    fun getAllCoursesByUser(userId: UUID, pageable: Pageable): ResponsePageDto<CourseDto>? {
        var result: ResponseEntity<ResponsePageDto<CourseDto>>? = null
        val url = COURSE_REQUEST_URI + ClientUtils.createUrlGetAllCoursesByUser(userId, pageable)

        LOGGER.debug("Request URL: $url")
        LOGGER.info("Request URL: $url")

        println("=== Start request do course api ===")

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

    private fun retryFallback(userId: UUID, pageable: Pageable, t: Throwable): ResponsePageDto<CourseDto>? {
        LOGGER.error("Error in retry fallback to user: $userId and error: $t")
        return ResponsePageDto<CourseDto>()
    }

    private fun circuitbreakerFallback(userId: UUID, pageable: Pageable, t: Throwable): ResponsePageDto<CourseDto>? {
        LOGGER.error("Error in circuit breaker fallback to user: $userId and error: $t")
        return ResponsePageDto<CourseDto>()
    }
}