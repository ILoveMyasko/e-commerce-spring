package com.example.exceptions;

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private String getRequestPath(WebRequest request) {
        if (request instanceof ServletWebRequest swr) {
            return swr.getRequest().getRequestURI();
        }
        return "N/A";
    }
    //copy-pasted from product-service
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @Nonnull HttpHeaders  headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        });

        ApiValidationErrorResponse errorResponse = new  ApiValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Validation Failed",
                getRequestPath(request),
                LocalDateTime.now(),
                errors
        );
        // log.warn("Validation failed for request {}: {}", getRequestPath(request), errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound( ResourceNotFoundException ex, WebRequest request)
    {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Conflict",
                ex.getMessage(),
                getRequestPath(request),
                LocalDateTime.now()
        );
        // log.warn("Duplicate resource conflict for request  {}: {}", getRequestPath(request), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceCommunicationException.class)
    public ResponseEntity<Object> handleServiceCommunicationFailed( ServiceCommunicationException ex, WebRequest request)
    {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Not Found",
                ex.getMessage(),
                getRequestPath(request),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public record ApiValidationErrorResponse(
            int status,
            String error,
            String message,
            String path,
            LocalDateTime timestamp,
            Map<String, String> fieldErrors
    ) {}

    public record ApiErrorResponse(
            int status,
            String error,
            String message,
            String path,
            LocalDateTime timestamp
    ) {}
}
