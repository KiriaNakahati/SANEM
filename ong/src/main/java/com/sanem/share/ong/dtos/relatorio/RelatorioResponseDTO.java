package com.sanem.share.ong.dtos.relatorio;

import com.sanem.share.ong.models.Relatorio;

import java.time.LocalDate;
import java.util.UUID;

public record RelatorioResponseDTO(
        UUID id,
        UUID funcionarioId,
        LocalDate dataEmissao
) {
    public RelatorioResponseDTO(Relatorio relatorio) {
        this(relatorio.getId(), relatorio.getFuncionario().getId(), relatorio.getDataEmissao());
    }
}