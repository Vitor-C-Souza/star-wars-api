package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.Planeta;

public record PlanetaDtoResPersonagem(
        Long id,
        String nome,
        String clima,
        String terreno,
        Integer populacao,
        SistemaEstelarDtoResPlaneta sistema
) {
    public PlanetaDtoResPersonagem(Planeta planeta) {
        this(
                planeta.getId(),
                planeta.getNome(),
                planeta.getClima(),
                planeta.getTerreno(),
                planeta.getPopulacao(),
                new SistemaEstelarDtoResPlaneta(planeta.getSistema())
        );
    }
}
