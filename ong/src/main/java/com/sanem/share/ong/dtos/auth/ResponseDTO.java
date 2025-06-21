package com.sanem.share.ong.dtos.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseDTO(
        String token
) {
}

