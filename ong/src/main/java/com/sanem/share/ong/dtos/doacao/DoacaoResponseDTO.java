package com.sanem.share.ong.dtos.doacao;

import com.sanem.share.ong.models.Doacao;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record DoacaoResponseDTO(
        UUID id,
        LocalDate dataDoacao,
        Integer situacaoGeral,
        List<DoacaoItemDTO> itens
) {
    public DoacaoResponseDTO(Doacao d) {
        this(
                d.getId(),
                d.getDataDoacao(),
                d.getSituacaoGeral(),
                d.getItensDoacao().stream()
                        .map(i -> new DoacaoItemDTO(i.getItem().getId(), i.getQuantidade()))
                        .collect(Collectors.toList())
        );
    }
}