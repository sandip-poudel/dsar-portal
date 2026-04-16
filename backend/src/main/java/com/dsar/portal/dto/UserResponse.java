package com.dsar.portal.dto;

import com.dsar.portal.model.Role;
import lombok.Builder;
import lombok.Data;

/** API response DTO for the authenticated user profile. */
@Data
@Builder
public class UserResponse {
    private String username;
    private String fullName;
    private String email;
    private Role role;
}
