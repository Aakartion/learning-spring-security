package edu.miu.cs489.securitydemo.auth.request;

public record AuthenticationRequestDTO(
        String username,
        String password
) {
}
