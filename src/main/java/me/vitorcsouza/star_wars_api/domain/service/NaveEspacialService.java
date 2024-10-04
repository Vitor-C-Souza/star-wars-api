package me.vitorcsouza.star_wars_api.domain.service;

import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NaveEspacialService {
    NaveEspacialDtoRes create(NaveEspacialDtoReq dto);
    NaveEspacialDtoRes findById(Long id);
    Page<NaveEspacialDtoRes> findAll(Pageable pageable);
    NaveEspacialDtoRes update(NaveEspacialDtoReq dtoReq, Long id);
    void delete(Long id);
}
