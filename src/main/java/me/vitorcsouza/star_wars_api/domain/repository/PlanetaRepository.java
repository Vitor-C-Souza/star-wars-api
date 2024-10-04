package me.vitorcsouza.star_wars_api.domain.repository;

import me.vitorcsouza.star_wars_api.domain.model.Planeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {
}
