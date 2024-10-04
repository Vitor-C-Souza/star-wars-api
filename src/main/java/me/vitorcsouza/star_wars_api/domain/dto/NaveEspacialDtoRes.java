package me.vitorcsouza.star_wars_api.domain.dto;

import me.vitorcsouza.star_wars_api.domain.model.NaveEspacial;

public record NaveEspacialDtoRes(
        Long id,
        String nome,
        String modelo,
        String fabricante,
        Integer capacidadePassageiros
) {
    public NaveEspacialDtoRes(NaveEspacial naveEspacial){
        this(
                naveEspacial.getId(),
                naveEspacial.getNome(),
                naveEspacial.getModelo(),
                naveEspacial.getFabricante(),
                naveEspacial.getCapacidadePassageiros()
        );
    }
}
