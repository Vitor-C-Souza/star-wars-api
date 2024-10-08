package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.Planeta;
import me.vitorcsouza.star_wars_api.domain.repository.PersonagemRepository;

import java.util.List;
import java.util.stream.Collectors;

public record PlanetaDtoRes(
        Long id,
        String nome,
        String clima,
        String terreno,
        Integer populacao,
        SistemaEstelarDtoResPlaneta sistema,
        List<PersonagemDtoResPlaneta> personagens
) {
    public PlanetaDtoRes(Planeta planeta, List<PersonagemDtoResPlaneta> personagens){
        this(
                planeta.getId(),
                planeta.getNome(),
                planeta.getClima(),
                planeta.getTerreno(),
                planeta.getPopulacao(),
                new SistemaEstelarDtoResPlaneta(planeta.getSistema()),
                personagens);
    }
}
