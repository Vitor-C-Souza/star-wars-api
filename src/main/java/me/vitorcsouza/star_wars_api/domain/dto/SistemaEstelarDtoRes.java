package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;

public record SistemaEstelarDtoRes(
        Long id,
        String nome,
        String descricao
) {
    public SistemaEstelarDtoRes(SistemaEstelar sistemaEstelar){
        this(
                sistemaEstelar.getId(),
                sistemaEstelar.getNome(),
                sistemaEstelar.getDescricao()
        );
    }
}
