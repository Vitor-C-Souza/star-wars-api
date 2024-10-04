package me.vitorcsouza.star_wars_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlanetaDtoReq(
        @NotNull @NotBlank String nome,
        @NotNull @NotBlank String clima,
        @NotNull @NotBlank String terreno,
        @NotNull @NotBlank Integer populacao,
        @NotNull @NotBlank Long sistema_id
) {
}
