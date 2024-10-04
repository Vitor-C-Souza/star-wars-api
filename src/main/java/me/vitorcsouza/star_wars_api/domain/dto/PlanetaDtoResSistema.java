package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.Planeta;

public record PlanetaDtoResSistema(
        Long id,
        String nome,
        String clima,
        String terreno,
        Integer populacao
) {
    public PlanetaDtoResSistema(Planeta planeta){
        this(
                planeta.getId(),
                planeta.getNome(),
                planeta.getClima(),
                planeta.getTerreno(),
                planeta.getPopulacao()
        );
    }
}
