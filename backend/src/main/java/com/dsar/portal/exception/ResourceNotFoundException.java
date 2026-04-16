package com.dsar.portal.exception;

/** Thrown when a requested resource (e.g. DsarRequest) does not exist. Maps to HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
