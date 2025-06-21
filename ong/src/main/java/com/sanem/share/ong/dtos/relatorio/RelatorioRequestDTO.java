package com.sanem.share.ong.dtos.relatorio;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RelatorioRequestDTO(
        @NotNull(message = "ID do funcionário é obrigatório")
        UUID funcionarioId,
        @NotNull(message = "Data de emissão é obrigatória")
        LocalDate dataEmissao
) {}
