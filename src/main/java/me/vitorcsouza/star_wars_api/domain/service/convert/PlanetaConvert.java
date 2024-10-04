package me.vitorcsouza.star_wars_api.domain.service.convert;

import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.Planeta;
import me.vitorcsouza.star_wars_api.domain.repository.PersonagemRepository;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;
import org.springframework.stereotype.Component;

@Component
public class PlanetaConvert {
    public Planeta toModel(PlanetaDtoReq dtoReq, SistemaEstelarRepository sistemaEstelarRepository){
        return new Planeta(dtoReq, sistemaEstelarRepository);
    }

    public PlanetaDtoRes toDto(Planeta planeta, PersonagemRepository personagemRepository){
        return new PlanetaDtoRes(planeta, personagemRepository);
    }
}
