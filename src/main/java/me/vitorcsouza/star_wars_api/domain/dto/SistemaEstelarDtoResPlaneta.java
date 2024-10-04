package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;

public record SistemaEstelarDtoResPlaneta(
        Long id,
        String nome,
        String descricao
) {
    public SistemaEstelarDtoResPlaneta(SistemaEstelar sistema) {
        this(
                sistema.getId(),
                sistema.getNome(),
                sistema.getDescricao()
        );
    }
}
