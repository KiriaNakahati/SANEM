package com.sanem.share.ong.dtos.item;

import jakarta.validation.constraints.NotBlank;

public record ItemRequestDTO(
        @NotBlank(message = "Categoria é obrigatória")
        String categoria,
        @NotBlank(message = "Tamanho é obrigatório")
        String tamanho
) {}