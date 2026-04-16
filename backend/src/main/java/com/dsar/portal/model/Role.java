package com.dsar.portal.model;

/**
 * Represents the role of a user in the DSAR portal.
 * CUSTOMER: Can submit and view their own requests.
 * ADMIN: Can manage all requests, update statuses, and view the audit trail.
 */
public enum Role {
    CUSTOMER,
    ADMIN
}
