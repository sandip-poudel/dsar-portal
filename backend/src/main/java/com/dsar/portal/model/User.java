package com.dsar.portal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an authenticated user in the DSAR portal.
 * Stored in-memory via UserRepository (ConcurrentHashMap).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;   // BCrypt-encoded
    private String fullName;
    private String email;
    private Role role;
}
