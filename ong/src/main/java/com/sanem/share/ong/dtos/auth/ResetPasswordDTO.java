package com.sanem.share.ong.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDTO(
        @NotBlank String newPassword
) {
}
