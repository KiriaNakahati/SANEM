package com.sanem.share.ong.dtos.beneficiario;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CartaoBeneficiarioRequestDTO(
        @NotNull(message = "ID do beneficiário é obrigatório")
        UUID beneficiarioId,
        @NotNull(message = "Data de emissão é obrigatória")
        LocalDate dataEmissao
) {}
