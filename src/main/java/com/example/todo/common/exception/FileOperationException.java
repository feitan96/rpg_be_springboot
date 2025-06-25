package com.example.todo.common.exception;

/**
 * Custom IOException that extends RuntimeException instead of checked Exception.
 * This allows for unchecked file operation exceptions that can be handled by the global handler.
 */
public class FileOperationException extends RuntimeException {

  public FileOperationException(String message) {
    super(message);
  }

  public FileOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}