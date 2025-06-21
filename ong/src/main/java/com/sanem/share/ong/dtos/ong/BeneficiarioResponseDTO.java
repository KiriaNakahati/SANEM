package com.sanem.share.ong.dtos.ong;

import com.sanem.share.ong.models.Beneficiario;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record BeneficiarioResponseDTO(
        UUID id,
        String telefone,
        UUID funcionarioId,
        String funcionarioNome,
        List<CartaoBeneficiarioDTO> cartoes
) {
    public BeneficiarioResponseDTO(Beneficiario beneficiario) {
        this(
                beneficiario.getId(),
                beneficiario.getTelefone(),
                beneficiario.getFuncionario().getId(),
                beneficiario.getFuncionario().getUsername(),
                beneficiario.getCartoes() != null ?
                        beneficiario.getCartoes().stream()
                                .map(CartaoBeneficiarioDTO::new)
                                .collect(Collectors.toList()) :
                        List.of()
        );
    }
}
