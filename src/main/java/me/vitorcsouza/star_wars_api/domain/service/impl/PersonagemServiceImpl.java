package me.vitorcsouza.star_wars_api.domain.service.impl;


import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.Personagem;
import me.vitorcsouza.star_wars_api.domain.repository.PersonagemRepository;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import me.vitorcsouza.star_wars_api.domain.service.PersonagemService;
import me.vitorcsouza.star_wars_api.domain.service.convert.PersonagemConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class PersonagemServiceImpl implements PersonagemService {
    @Autowired
    private PersonagemRepository repository;
    @Autowired
    private PlanetaRepository planetaRepository;
    @Autowired
    private PersonagemConvert convert;

    @Transactional
    @Override
    public PersonagemDtoRes create(PersonagemDtoReq dto) {
        Personagem personagem = convert.toModel(dto, planetaRepository);
        repository.save(personagem);
        return convert.toDto(personagem);
    }

    @Transactional(readOnly = true)
    @Override
    public PersonagemDtoRes findById(Long id) {
        Personagem personagem = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return convert.toDto(personagem);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PersonagemDtoRes> findAll(Pageable pageable) {
        Page<Personagem> personagemPage = repository.findAll(pageable);
        return personagemPage.map(PersonagemDtoRes::new);
    }

    @Transactional
    @Override
    public PersonagemDtoRes update(PersonagemDtoReq dtoReq, Long id) {
        Personagem referenceById = repository.getReferenceById(id);
        referenceById.createOrUpdate(dtoReq, planetaRepository);
        repository.save(referenceById);
        return convert.toDto(referenceById);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
