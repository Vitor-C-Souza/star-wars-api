package me.vitorcsouza.star_wars_api.domain.service;

import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SistemaEstelarService {
    SistemaEstelarDtoRes create(SistemaEstelarDtoReq dto);
    SistemaEstelarDtoRes findById(Long id);
    Page<SistemaEstelarDtoRes> findAll(Pageable pageable);
    SistemaEstelarDtoRes update(SistemaEstelarDtoReq dtoReq, Long id);
    void delete(Long id);
}
