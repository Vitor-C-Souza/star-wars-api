package me.vitorcsouza.star_wars_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoReq;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;

import java.util.NoSuchElementException;

@Entity(name = "personagem_tb")
@Getter
@NoArgsConstructor
public class Personagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String raca;
    @Column(nullable = false)
    private String afiliacao;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Planeta planetaNatal;

    public Personagem(PersonagemDtoReq dtoReq, PlanetaRepository planetaRepository){
        createOrUpdate(dtoReq, planetaRepository);
    }

    public void createOrUpdate(PersonagemDtoReq dtoReq, PlanetaRepository planetaRepository) {
        this.nome = dtoReq.nome();
        this.raca = dtoReq.raca();
        this.afiliacao = dtoReq.afiliacao();
        this.planetaNatal = planetaRepository.findById(dtoReq.planetaNatal_id()).orElseThrow(NoSuchElementException::new);
    }
}
