package com.sanem.share.ong.dtos.ong;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateItemDTO(@NotNull UUID funcionarioId,
                            @NotBlank String categoria,
                            @NotBlank String tamanho) {
}
