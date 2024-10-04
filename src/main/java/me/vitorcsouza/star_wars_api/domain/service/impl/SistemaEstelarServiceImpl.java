package me.vitorcsouza.star_wars_api.domain.service.impl;


import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;
import me.vitorcsouza.star_wars_api.domain.service.SistemaEstelarService;
import me.vitorcsouza.star_wars_api.domain.service.convert.SistemaEstelarConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class SistemaEstelarServiceImpl implements SistemaEstelarService {
    @Autowired
    private SistemaEstelarRepository repository;
    @Autowired
    private PlanetaRepository planetaRepository;
    @Autowired
    private SistemaEstelarConvert convert;

    @Transactional
    @Override
    public SistemaEstelarDtoRes create(SistemaEstelarDtoReq dto) {
        SistemaEstelar sistemaEstelar = convert.toModel(dto);
        repository.save(sistemaEstelar);
        return convert.toDto(sistemaEstelar, planetaRepository);
    }

    @Transactional(readOnly = true)
    @Override
    public SistemaEstelarDtoRes findById(Long id) {
        SistemaEstelar sistemaEstelar = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return convert.toDto(sistemaEstelar, planetaRepository);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SistemaEstelarDtoRes> findAll(Pageable pageable) {
        Page<SistemaEstelar> sistemaEstelarPage = repository.findAll(pageable);
        return sistemaEstelarPage.map(sistemaEstelar -> new SistemaEstelarDtoRes(sistemaEstelar, planetaRepository));
    }

    @Transactional
    @Override
    public SistemaEstelarDtoRes update(SistemaEstelarDtoReq dtoReq, Long id) {
        SistemaEstelar referenceById = repository.getReferenceById(id);
        referenceById.createOrUpdate(dtoReq);
        repository.save(referenceById);
        return convert.toDto(referenceById, planetaRepository);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
