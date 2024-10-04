package me.vitorcsouza.star_wars_api.domain.repository;

import me.vitorcsouza.star_wars_api.domain.model.Planeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {

    @Query(value = "SELECT * FROM planeta_tb WHERE sistema_id = :sistema_id;", nativeQuery = true)
    List<Planeta> findAllPlanetasDoSistema(Long sistema_id);
}
