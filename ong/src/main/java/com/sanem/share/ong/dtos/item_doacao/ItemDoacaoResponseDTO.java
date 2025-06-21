package com.sanem.share.ong.dtos.item_doacao;

import com.sanem.share.ong.models.ItemDoacao;

import java.time.LocalDate;
import java.util.UUID;

public record ItemDoacaoResponseDTO(
        UUID id,
        UUID doacaoId,
        UUID itemId,
        LocalDate dataDoacao,
        Integer quantidade
) {
    public ItemDoacaoResponseDTO(ItemDoacao itemDoacao) {
        this(itemDoacao.getId(),
                itemDoacao.getDoacao().getId(),
                itemDoacao.getItem().getId(),
                itemDoacao.getDataDoacao(),
                itemDoacao.getQuantidade());
    }
}