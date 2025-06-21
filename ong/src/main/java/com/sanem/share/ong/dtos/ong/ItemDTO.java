package com.sanem.share.ong.dtos.ong;

import com.sanem.share.ong.models.Item;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ItemDTO(
        @NotBlank UUID id,
        @NotBlank UUID funcionarioId,
        @NotBlank String categoria,
        @NotBlank String tamanho
) {
    public ItemDTO(Item item) {
        this(
                item.getId(),
                item.getFuncionario().getId(),
                item.getCategoria(),
                item.getTamanho()
        );
    }
}
