package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;

import java.util.List;
import java.util.stream.Collectors;

public record SistemaEstelarDtoRes(
        Long id,
        String nome,
        String descricao,
        List<PlanetaDtoResSistema> planetas
) {
    public SistemaEstelarDtoRes(SistemaEstelar sistemaEstelar, PlanetaRepository planetaRepository) {
        this(
                sistemaEstelar.getId(),
                sistemaEstelar.getNome(),
                sistemaEstelar.getDescricao(),
                planetaRepository.findAllPlanetasDoSistema(sistemaEstelar.getId())
                        .stream()
                        .map(PlanetaDtoResSistema::new)
                        .collect(Collectors.toList())
        );
    }
}
