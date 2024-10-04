package me.vitorcsouza.star_wars_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoReq;

@Entity(name = "nave_espacial_tb")
@Getter
@NoArgsConstructor
public class NaveEspacial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String modelo;
    @Column(nullable = false)
    private String fabricante;
    @Column(nullable = false)
    private Integer capacidadePassageiros;

    public NaveEspacial(NaveEspacialDtoReq dtoReq){
        createOrUpdate(dtoReq);
    }

    public void createOrUpdate(NaveEspacialDtoReq dtoReq) {
        this.nome = dtoReq.nome();
        this.capacidadePassageiros = dtoReq.capacidadePassageiros();
        this.modelo = dtoReq.modelo();
        this.fabricante = dtoReq.fabricante();
    }
}
