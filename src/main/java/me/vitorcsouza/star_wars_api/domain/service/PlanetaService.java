package me.vitorcsouza.star_wars_api.domain.service;

import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanetaService {
    PlanetaDtoRes create(PlanetaDtoReq dto);
    PlanetaDtoRes findById(Long id);
    Page<PlanetaDtoRes> findAll(Pageable pageable);
    PlanetaDtoRes update(PlanetaDtoReq dtoReq, Long id);
    void delete(Long id);
}
