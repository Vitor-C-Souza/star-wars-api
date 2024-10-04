package me.vitorcsouza.star_wars_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoReq;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Entity(name = "planeta_tb")
@Getter
@NoArgsConstructor
public class Planeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String clima;
    @Column(nullable = false)
    private String terreno;
    @Column(nullable = false)
    private Integer populacao;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SistemaEstelar sistema;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "planetaNatal")
    private List<Personagem> personagem;

    public Planeta(PlanetaDtoReq dtoReq, SistemaEstelarRepository sistemaEstelarRepository){
        createOrUpdate(dtoReq, sistemaEstelarRepository);
    }

    public void createOrUpdate(PlanetaDtoReq dtoReq, SistemaEstelarRepository sistemaEstelarRepository) {
        this.clima = dtoReq.clima();
        this.nome = dtoReq.nome();
        this.sistema = sistemaEstelarRepository.findById(dtoReq.sistema_id()).orElseThrow(NoSuchElementException::new);
        this.terreno = dtoReq.terreno();
        this.populacao = dtoReq.populacao();
    }
}
