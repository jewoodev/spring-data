package data.spring.mybatis.adapter.in;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalControllerAdvice {

    // ==================== 400 Bad Request ====================

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalArgumentException(IllegalArgumentException e) {
        return ErrorResponse.of(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErrorResponse.of(
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ErrorResponse handlerMethodValidationException(HandlerMethodValidationException e) {
        return ErrorResponse.of(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse httpMessageNotReadableException(HttpMessageNotReadableException e) {
        // 원인 예외 추출
        String message = extractRootCauseMessage(e);
        return ErrorResponse.of(message);
    }

    private String extractRootCauseMessage(Throwable e) {
        Throwable cause = e;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }


    // ==================== 500 Internal Server Error ====================

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handle(Exception e) {
        return ErrorResponse.of(e.getMessage());
    }
}
