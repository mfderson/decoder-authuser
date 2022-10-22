package com.ead.authuser.exceptions

import com.ead.authuser.exceptions.dto.ErrorDTO
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import kotlin.Exception

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.NOT_FOUND
        val body = ErrorDTO(
            title = ErrorsEnum.NOT_FOUND_EXCEPTION.title,
            status = status.value(),
            detail = ex.message
        )
        return handleExceptionInternal(ex, body, HttpHeaders(), status, request)
    }

    @ExceptionHandler(UsernameAlreadyTakenException::class)
    fun handleEntityNotFoundException(ex: UsernameAlreadyTakenException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.CONFLICT
        val body = ErrorDTO(
            title = ErrorsEnum.USERNAME_ALREADY_TAKEN_EXCEPTION.title,
            status = status.value(),
            detail = ex.message
        )
        return handleExceptionInternal(ex, body, HttpHeaders(), status, request)
    }

    @ExceptionHandler(EmailAlreadyTakenException::class)
    fun handleEntityNotFoundException(ex: EmailAlreadyTakenException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.CONFLICT
        val body = ErrorDTO(
            title = ErrorsEnum.EMAIL_ALREADY_TAKEN_EXCEPTION.title,
            status = status.value(),
            detail = ex.message
        )
        return handleExceptionInternal(ex, body, HttpHeaders(), status, request)
    }

    @ExceptionHandler(MismatchedOldPasswordException::class)
    fun handleEntityNotFoundException(ex: MismatchedOldPasswordException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.CONFLICT
        val body = ErrorDTO(
            title = ErrorsEnum.MISMATCHED_OLD_PASSWORD.title,
            status = status.value(),
            detail = ex.message
        )
        return handleExceptionInternal(ex, body, HttpHeaders(), status, request)
    }

    @ExceptionHandler(Exception::class)
    fun handleUncaught(ex: Exception, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val body = ErrorDTO(
            title = ErrorsEnum.INTERNAL_SERVER_EXCEPTION.title,
            status = status.value(),
            detail = ex.message
        )

        return handleExceptionInternal(ex, body, HttpHeaders(), status, request)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val newBody = body?.let {
            ErrorDTO(
                title = status.reasonPhrase,
                status = status.value()
            )
        } ?: run {
            if (body is String) ErrorDTO(
                title = status.reasonPhrase,
                status = status.value())
        }

        return super.handleExceptionInternal(ex, body, headers, status, request)
    }
}