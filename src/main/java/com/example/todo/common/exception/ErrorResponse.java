package com.example.todo.common.exception;

import java.time.LocalDateTime;

/**
 * Represents a standardized error response for API errors.
 * This class encapsulates the error details that will be returned to the client.
 */
public class ErrorResponse {
  private LocalDateTime timestamp;
  private String error;
  private String message;

  /**
   * Constructs an ErrorResponse with the current timestamp, error type, and message.
   *
   * @param error   The type of error (e.g., "Not Found", "Bad Request").
   * @param message A detailed message describing the error.
   */
  public ErrorResponse(String error, String message) {
    this.timestamp = LocalDateTime.now();
    this.error = error;
    this.message = message;
  }

  // Getters
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }
}