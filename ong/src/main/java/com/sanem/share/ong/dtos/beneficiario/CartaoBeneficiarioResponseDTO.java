package com.sanem.share.ong.dtos.beneficiario;

import com.sanem.share.ong.models.CartaoBeneficiario;

import java.time.LocalDate;
import java.util.UUID;

public record CartaoBeneficiarioResponseDTO(
        UUID id,
        UUID beneficiarioId,
        LocalDate dataEmissao
) {
    public CartaoBeneficiarioResponseDTO(CartaoBeneficiario cartao) {
        this(cartao.getId(),
                cartao.getBeneficiario().getId(),
                cartao.getDataEmissao());
    }
}