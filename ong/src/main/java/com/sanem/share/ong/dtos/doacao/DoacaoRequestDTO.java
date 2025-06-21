package com.sanem.share.ong.dtos.doacao;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import java.util.List;

public record DoacaoRequestDTO(
        @NotNull(message = "Data da doação é obrigatória")
        LocalDate dataDoacao,

        @NotNull(message = "Situação geral é obrigatória")
        Integer situacaoGeral,

        @NotEmpty(message = "Deve haver pelo menos um item na doação")
        List<DoacaoItemDTO> itens
) {}