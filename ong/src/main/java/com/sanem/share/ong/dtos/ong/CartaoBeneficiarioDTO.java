package com.sanem.share.ong.dtos.ong;

import com.sanem.share.ong.models.CartaoBeneficiario;

import java.time.LocalDate;
import java.util.UUID;

public record CartaoBeneficiarioDTO(
        UUID id,
        LocalDate dataEmissao
) {
    public CartaoBeneficiarioDTO(CartaoBeneficiario cartao) {
        this(
                cartao.getId(),
                cartao.getDataEmissao()
        );
    }
}
