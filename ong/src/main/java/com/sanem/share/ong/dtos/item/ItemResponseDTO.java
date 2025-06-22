package com.sanem.share.ong.dtos.item;

import com.sanem.share.ong.models.Item;

import java.util.UUID;

public record ItemResponseDTO(
        UUID id,
        UUID funcionarioId,
        String categoria,
        String tamanho) {
    public ItemResponseDTO(Item item) {
        this(item.getId(),
                item.getFuncionario().getId(),
                item.getCategoria(),
                item.getTamanho());
    }
}