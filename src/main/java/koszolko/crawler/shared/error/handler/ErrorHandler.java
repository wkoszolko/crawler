package koszolko.crawler.shared.error.handler;

import brave.Tracer;
import koszolko.crawler.shared.error.dto.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
class ErrorHandler {

    private final Clock clock;
    private final DateTimeFormatter dateTimeFormatter;
    @Autowired
    private Tracer tracer;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails processValidationError(MethodArgumentNotValidException ex) {
        ErrorDetails errorDetails = buildErrorDetails("Validation error.");
        log.error("Validation error.", ex);
        return errorDetails;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails serverError(Exception ex) {
        ErrorDetails errorDetails = buildErrorDetails("Server error.");
        log.error("Error occurred!", ex);
        return errorDetails;
    }

    private String getTraceId() {
        return tracer.currentSpan().context().traceIdString();
    }

    private ErrorDetails buildErrorDetails(String responseMessage) {
        return ErrorDetails
                .builder()
                .timestamp(dateTimeFormatter.format(now(clock)))
                .message(responseMessage)
                .traceId(getTraceId())
                .build();
    }
}
