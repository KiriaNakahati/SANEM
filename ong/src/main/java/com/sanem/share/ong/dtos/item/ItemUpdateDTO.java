package com.sanem.share.ong.dtos.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItemUpdateDTO(
        @NotNull(message = "ID do item é obrigatório")
        UUID id,
        @NotNull(message = "ID do funcionário é obrigatório")
        UUID funcionarioId,
        @NotBlank(message = "Categoria é obrigatória")
        String categoria,
        @NotBlank(message = "Tamanho é obrigatório")
        String tamanho
) {}
