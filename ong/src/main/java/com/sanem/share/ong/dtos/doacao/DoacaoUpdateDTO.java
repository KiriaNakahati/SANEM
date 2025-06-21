package com.sanem.share.ong.dtos.doacao;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record DoacaoUpdateDTO(
        @NotNull(message = "ID da doação é obrigatório")
        UUID id,
        @NotNull(message = "Data da doação é obrigatória")
        LocalDate dataDoacao,
        @NotNull(message = "Situação geral é obrigatória")
        Integer situacaoGeral
) {}