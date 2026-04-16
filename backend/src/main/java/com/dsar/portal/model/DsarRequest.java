package com.dsar.portal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Core domain entity representing a Data Subject Access Request.
 * The dueDate is auto-set to createdAt + 30 days (configurable via dsar.sla.days).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DsarRequest {
    private String id;              // UUID
    private String fullName;        // Legal name of data subject
    private String email;           // Contact email of data subject
    private RequestType type;       // ACCESS | DELETE | CORRECT
    private String description;     // Detailed description of the request
    private RequestStatus status;   // Lifecycle status
    private String submittedBy;     // Username of the customer who submitted
    private String processedBy;     // Username of admin who last updated
    private String adminNotes;      // Compliance notes added by admin
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;  // SLA deadline (createdAt + 30 days)
}
