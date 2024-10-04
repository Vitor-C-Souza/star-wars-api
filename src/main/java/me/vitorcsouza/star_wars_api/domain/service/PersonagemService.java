package me.vitorcsouza.star_wars_api.domain.service;

import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonagemService {
    PersonagemDtoRes create(PersonagemDtoReq dto);
    PersonagemDtoRes findById(Long id);
    Page<PersonagemDtoRes> findAll(Pageable pageable);
    PersonagemDtoRes update(PersonagemDtoReq dtoReq, Long id);
    void delete(Long id);
}
