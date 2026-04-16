package com.dsar.portal.dto;

import com.dsar.portal.model.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/** API response DTO for an audit log entry. */
@Data
@Builder
public class AuditLogResponse {
    private String id;
    private String requestId;
    private String actorUsername;
    private Role actorRole;
    private String action;
    private String details;
    private String previousValue;
    private String newValue;
    private LocalDateTime timestamp;
}
