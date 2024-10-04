package me.vitorcsouza.star_wars_api.domain.service.convert;

import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.NaveEspacial;
import org.springframework.stereotype.Component;

@Component
public class NaveEspacialConvert {
    public NaveEspacial toModel(NaveEspacialDtoReq dtoReq){
        return new NaveEspacial(dtoReq);
    }

    public NaveEspacialDtoRes toDto(NaveEspacial naveEspacial){
        return new NaveEspacialDtoRes(naveEspacial);
    }
}
