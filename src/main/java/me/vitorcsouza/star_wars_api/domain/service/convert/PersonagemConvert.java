package me.vitorcsouza.star_wars_api.domain.service.convert;

import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.Personagem;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import org.springframework.stereotype.Component;

@Component
public class PersonagemConvert {
    public Personagem toModel(PersonagemDtoReq dtoReq, PlanetaRepository planetaRepository){
        return new Personagem(dtoReq, planetaRepository);
    }

    public PersonagemDtoRes toDto(Personagem personagem){
        return new PersonagemDtoRes(personagem);
    }
}
