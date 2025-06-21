package com.sanem.share.ong.dtos.doacao;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DoacaoItemDTO(
        @NotNull(message = "ID do item é obrigatório")
        UUID itemId,

        @NotNull(message = "Quantidade é obrigatória")
        Integer quantidade
) {}
