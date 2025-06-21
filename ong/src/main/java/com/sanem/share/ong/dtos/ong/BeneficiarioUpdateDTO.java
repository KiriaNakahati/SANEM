package com.sanem.share.ong.dtos.ong;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BeneficiarioUpdateDTO(
        @NotNull(message = "O ID do beneficiário é obrigatório")
        UUID id,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotNull(message = "O ID do funcionário é obrigatório")
        UUID funcionarioId
) {
}
