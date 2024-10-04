package me.vitorcsouza.star_wars_api.domain.service.impl;


import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.NaveEspacial;
import me.vitorcsouza.star_wars_api.domain.repository.NaveEspacialRepository;
import me.vitorcsouza.star_wars_api.domain.service.NaveEspacialService;
import me.vitorcsouza.star_wars_api.domain.service.convert.NaveEspacialConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class NaveEspacialServiceImpl implements NaveEspacialService {
    @Autowired
    private NaveEspacialRepository repository;
    @Autowired
    private NaveEspacialConvert convert;

    @Transactional
    @Override
    public NaveEspacialDtoRes create(NaveEspacialDtoReq dto) {
        NaveEspacial naveEspacial = convert.toModel(dto);
        repository.save(naveEspacial);
        return convert.toDto(naveEspacial);
    }

    @Transactional(readOnly = true)
    @Override
    public NaveEspacialDtoRes findById(Long id) {
        NaveEspacial naveEspacial = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return convert.toDto(naveEspacial);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<NaveEspacialDtoRes> findAll(Pageable pageable) {
        Page<NaveEspacial> naveEspacialPage = repository.findAll(pageable);
        return naveEspacialPage.map(NaveEspacialDtoRes::new);
    }

    @Transactional
    @Override
    public NaveEspacialDtoRes update(NaveEspacialDtoReq dtoReq, Long id) {
        NaveEspacial referenceById = repository.getReferenceById(id);
        referenceById.createOrUpdate(dtoReq);
        repository.save(referenceById);
        return convert.toDto(referenceById);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
