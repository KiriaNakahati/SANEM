package com.sanem.share.ong.dtos.ong;

import jakarta.validation.constraints.NotBlank;

public record UpdateItemDTO(@NotBlank String categoria,
                            @NotBlank String tamanho) {
}
