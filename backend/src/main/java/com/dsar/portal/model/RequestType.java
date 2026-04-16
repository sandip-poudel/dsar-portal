package com.dsar.portal.model;

/**
 * The type of Data Subject Access Request.
 * ACCESS  - Subject requests a copy of all personal data held.
 * DELETE  - Subject requests erasure of personal data (GDPR Art. 17).
 * CORRECT - Subject requests correction of inaccurate personal data (GDPR Art. 16).
 */
public enum RequestType {
    ACCESS,
    DELETE,
    CORRECT
}
