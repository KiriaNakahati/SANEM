package com.sanem.share.ong.dtos.ong;

import jakarta.validation.constraints.NotBlank;

public record BeneficiarioRequestDTO(
        @NotBlank(message = "O telefone é obrigatório")
        String telefone,
        @NotBlank(message = "O Nome do beneficiado é obrigatório")
        String nome
) {
}
