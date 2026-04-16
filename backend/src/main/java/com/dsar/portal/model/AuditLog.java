package com.dsar.portal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Immutable audit trail entry.
 * Every mutation to a DsarRequest generates an AuditLog entry.
 * Used for GDPR compliance evidence.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    private String id;              // UUID
    private String requestId;       // FK to DsarRequest.id
    private String actorUsername;   // Who performed the action
    private Role actorRole;         // Role of the actor at time of action
    private String action;          // e.g. REQUEST_SUBMITTED, STATUS_CHANGED
    private String details;         // Human-readable description
    private String previousValue;   // Value before change (nullable)
    private String newValue;        // Value after change (nullable)
    private LocalDateTime timestamp;
}
