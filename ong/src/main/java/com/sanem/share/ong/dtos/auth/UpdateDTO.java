package com.sanem.share.ong.dtos.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateDTO(
        String first_name,
        String last_name,
        String cpf,
        String email,
        String password
) {
}
