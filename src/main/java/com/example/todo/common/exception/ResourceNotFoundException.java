package com.example.todo.common.exception;

/**
 * Exception thrown when a requested resource is not found.
 * This can be used for any entity that needs to indicate a missing resource.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceType, Long id) {
        super(resourceType + " with id " + id + " not found");
    }
}