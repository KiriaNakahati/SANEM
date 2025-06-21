package com.sanem.share.ong.dtos.item_doacao;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ItemDoacaoUpdateDTO(
        @NotNull UUID id,
        @NotNull UUID doacaoId,
        @NotNull UUID itemId,
        @NotNull LocalDate dataDoacao,
        @NotNull Integer quantidade
) {}
