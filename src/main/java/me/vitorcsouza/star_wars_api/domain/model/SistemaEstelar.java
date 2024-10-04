package me.vitorcsouza.star_wars_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoReq;

import java.util.List;

@Entity(name = "sistema_estelar_tp")
@Getter
@NoArgsConstructor
public class SistemaEstelar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "sistema")
    private List<Planeta> planetas;

    public SistemaEstelar(SistemaEstelarDtoReq dtoReq){
        createOrUpdate(dtoReq);
    }

    public void createOrUpdate(SistemaEstelarDtoReq dtoReq){
        this.nome = dtoReq.nome();
        this.descricao = dtoReq.descricao();
    }
}
