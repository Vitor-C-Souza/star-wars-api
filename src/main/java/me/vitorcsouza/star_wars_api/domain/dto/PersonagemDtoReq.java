package me.vitorcsouza.star_wars_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonagemDtoReq(
        @NotBlank @NotNull String nome,
        @NotBlank @NotNull String raca,
        @NotBlank @NotNull String afiliacao,
        @NotNull Long planetaNatal_id
) {
}
