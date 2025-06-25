package com.example.todo.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Global exception handler for the application.
 * Centralizes exception handling across all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles resource not found exceptions.
     *
     * @param ex the exception
     * @return 404 NOT FOUND with error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("Resource not found", ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handles invalid request data exceptions.
     *
     * @param ex the exception
     * @return 400 BAD REQUEST with error details
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex) {
        log.error("Invalid request: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("Invalid request", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles file upload size exceeded exceptions.
     *
     * @param ex the exception
     * @return 413 PAYLOAD TOO LARGE with error details
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.error("File upload too large: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("File too large",
                "The uploaded file exceeds the maximum allowed size"),
                HttpStatus.PAYLOAD_TOO_LARGE);
    }

    /**
     * Handles all other uncaught exceptions.
     *
     * @param ex the exception
     * @return 500 INTERNAL SERVER ERROR with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return new ResponseEntity<>(new ErrorResponse("Internal server error",
                "An unexpected error occurred. Please try again later."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles file operation exceptions.
     *
     * @param ex the exception
     * @return 500 INTERNAL SERVER ERROR with error details
     */
    @ExceptionHandler(FileOperationException.class)
    public ResponseEntity<ErrorResponse> handleFileOperationException(FileOperationException ex) {
        log.error("File operation error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("File operation error",
                "An error occurred during file operation"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles standard Java IO exceptions.
     *
     * @param ex the exception
     * @return 500 INTERNAL SERVER ERROR with error details
     */
    @ExceptionHandler(java.io.IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(java.io.IOException ex) {
        log.error("IO operation error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("IO error",
                "An error occurred during IO operation"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}