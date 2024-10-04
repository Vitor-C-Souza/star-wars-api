package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.Personagem;

public record PersonagemDtoResPlaneta(
        Long id,
        String nome,
        String raca,
        String afiliacao
) {
    public PersonagemDtoResPlaneta(Personagem personagem){
        this(
                personagem.getId(),
                personagem.getNome(),
                personagem.getRaca(),
                String.valueOf(personagem.getAfiliacao())
        );
    }
}
