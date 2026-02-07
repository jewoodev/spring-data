package data.spring.mybatis.adapter.`in`

import data.spring.mybatis.adapter.`in`.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException

@RestControllerAdvice
class GlobalControllerAdvice {

    // ==================== 400 Bad Request ====================
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(e: IllegalArgumentException): ErrorResponse {
        return ErrorResponse(e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorResponse {
        return ErrorResponse(
            e.bindingResult.allErrors[0].defaultMessage
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handlerMethodValidationException(e: HandlerMethodValidationException): ErrorResponse {
        return ErrorResponse(e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableException(e: HttpMessageNotReadableException): ErrorResponse {
        return extractRootCauseMessage(e).let(::ErrorResponse)
    }

    private fun extractRootCauseMessage(e: Throwable): String? {
        var cause = e
        while (cause.cause != null) {
            cause = cause.cause!!
        }
        return cause.message
    }

    // ==================== 500 Internal Server Error ====================
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handle(e: Exception): ErrorResponse {
        return ErrorResponse(e.message)
    }
}