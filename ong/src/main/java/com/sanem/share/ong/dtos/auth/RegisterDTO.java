package com.sanem.share.ong.dtos.auth;

import jakarta.validation.constraints.NotBlank;


public record RegisterDTO(
        @NotBlank
        String first_name,
        @NotBlank
        String last_name,
        @NotBlank
        String email,
        @NotBlank
        String cpf,
        @NotBlank
        String password
) {
}
