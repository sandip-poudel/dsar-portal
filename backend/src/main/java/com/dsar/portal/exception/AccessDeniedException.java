package com.dsar.portal.exception;

/** Thrown when a customer tries to access another customer's request. Maps to HTTP 403. */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
