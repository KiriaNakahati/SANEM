package com.sanem.share.ong.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EmailDTO(
        @NotNull
        UUID userId,
        @NotNull
        @Email
        String emailTo,
        @NotNull
        String subject,
        String body
) {
}
