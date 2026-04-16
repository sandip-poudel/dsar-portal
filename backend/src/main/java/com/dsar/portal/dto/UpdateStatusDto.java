package com.dsar.portal.dto;

import com.dsar.portal.model.RequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for admin to update a DSAR request status.
 * PUT to /api/requests/{id}/status.
 */
@Data
public class UpdateStatusDto {

    @NotNull(message = "Status is required")
    private RequestStatus status;

    /** Optional compliance notes added by the admin. */
    private String adminNotes;
}
