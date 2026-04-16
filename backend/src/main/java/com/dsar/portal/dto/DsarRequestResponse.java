package com.dsar.portal.dto;

import com.dsar.portal.model.RequestStatus;
import com.dsar.portal.model.RequestType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/** API response DTO for a DSAR request. */
@Data
@Builder
public class DsarRequestResponse {
    private String id;
    private String fullName;
    private String email;
    private RequestType type;
    private String description;
    private RequestStatus status;
    private String submittedBy;
    private String processedBy;
    private String adminNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
}
