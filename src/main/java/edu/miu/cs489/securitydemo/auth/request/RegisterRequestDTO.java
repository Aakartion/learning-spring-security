package edu.miu.cs489.securitydemo.auth.request;

import edu.miu.cs489.securitydemo.user.Role;

public record RegisterRequestDTO(
        String firstName,
        String lastName,
        String username,
        String password,
        Role role
) {
}
