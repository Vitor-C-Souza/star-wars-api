package me.vitorcsouza.star_wars_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SistemaEstelarDtoReq(
        @NotBlank @NotNull String nome,
        @NotBlank @NotNull String descricao
) {
}
