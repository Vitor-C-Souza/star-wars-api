package me.vitorcsouza.star_wars_api.domain.service.impl;


import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.Planeta;
import me.vitorcsouza.star_wars_api.domain.repository.PersonagemRepository;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;
import me.vitorcsouza.star_wars_api.domain.service.PlanetaService;
import me.vitorcsouza.star_wars_api.domain.service.convert.PlanetaConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class PlanetaServiceImpl implements PlanetaService {
    @Autowired
    private PlanetaRepository repository;
    @Autowired
    private SistemaEstelarRepository sistemaEstelarRepository;
    @Autowired
    private PersonagemRepository personagemRepository;
    @Autowired
    private PlanetaConvert convert;

    @Transactional
    @Override
    public PlanetaDtoRes create(PlanetaDtoReq dto) {
        Planeta planeta = convert.toModel(dto, sistemaEstelarRepository);
        repository.save(planeta);
        return convert.toDto(planeta, personagemRepository);
    }

    @Transactional(readOnly = true)
    @Override
    public PlanetaDtoRes findById(Long id) {
        Planeta planeta = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return convert.toDto(planeta, personagemRepository);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PlanetaDtoRes> findAll(Pageable pageable) {
        Page<Planeta> planetaPage = repository.findAll(pageable);
        return planetaPage.map(planeta -> new PlanetaDtoRes(planeta, personagemRepository));
    }

    @Transactional
    @Override
    public PlanetaDtoRes update(PlanetaDtoReq dtoReq, Long id) {
        Planeta referenceById = repository.getReferenceById(id);
        referenceById.createOrUpdate(dtoReq, sistemaEstelarRepository);
        repository.save(referenceById);
        return convert.toDto(referenceById, personagemRepository);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
