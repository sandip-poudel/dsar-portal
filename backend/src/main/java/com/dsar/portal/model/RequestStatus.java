package com.dsar.portal.model;

/**
 * Lifecycle status of a DSAR request.
 * SUBMITTED  - Initial state when customer submits.
 * IN_REVIEW  - Admin is actively processing the request.
 * COMPLETED  - Request has been fulfilled.
 * REJECTED   - Request was rejected (with reason in adminNotes).
 */
public enum RequestStatus {
    SUBMITTED,
    IN_REVIEW,
    COMPLETED,
    REJECTED
}
