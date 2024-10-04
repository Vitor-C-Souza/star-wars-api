package me.vitorcsouza.star_wars_api.domain.service.convert;

import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;
import org.springframework.stereotype.Component;

@Component
public class SistemaEstelarConvert {
    public SistemaEstelar toModel(SistemaEstelarDtoReq dtoReq){
        return new SistemaEstelar(dtoReq);
    }

    public SistemaEstelarDtoRes toDto(SistemaEstelar sistemaEstelar){
        return new SistemaEstelarDtoRes(sistemaEstelar);
    }
}
