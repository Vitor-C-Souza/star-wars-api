package me.vitorcsouza.star_wars_api.domain.repository;

import me.vitorcsouza.star_wars_api.domain.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    @Query(value = "SELECT * FROM personagem_tb WHERE planeta_natal_id = :id", nativeQuery = true)
    List<Personagem> findByPlaneta(Long id);
}
