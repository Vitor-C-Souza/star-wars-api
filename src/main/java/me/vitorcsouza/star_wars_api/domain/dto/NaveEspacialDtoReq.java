package me.vitorcsouza.star_wars_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NaveEspacialDtoReq(
        @NotNull @NotBlank String nome,
        @NotNull @NotBlank String modelo,
        @NotNull @NotBlank String fabricante,
        @NotNull @NotBlank Integer capacidadePassageiros

) {
}
