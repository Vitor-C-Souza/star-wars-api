package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.Personagem;
import me.vitorcsouza.star_wars_api.domain.model.Planeta;

public record PersonagemDtoRes(
        Long id,
        String nome,
        String raca,
        String afiliacao,
        Planeta planeta_natal
) {
    public PersonagemDtoRes(Personagem personagem){
        this(
                personagem.getId(),
                personagem.getNome(),
                personagem.getRaca(),
                String.valueOf(personagem.getAfiliacao()),
                personagem.getPlanetaNatal()
        );
    }
}
