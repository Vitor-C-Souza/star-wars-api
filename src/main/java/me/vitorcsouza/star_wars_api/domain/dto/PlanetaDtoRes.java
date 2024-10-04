package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.Planeta;
import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;

public record PlanetaDtoRes(
        Long id,
        String nome,
        String clima,
        String terreno,
        Integer populacao,
        SistemaEstelarDtoRes sistema
) {
    public PlanetaDtoRes(Planeta planeta){
        this(
                planeta.getId(),
                planeta.getNome(),
                planeta.getClima(),
                planeta.getTerreno(),
                planeta.getPopulacao(),
                new SistemaEstelarDtoRes(planeta.getSistema())
        );
    }
}
