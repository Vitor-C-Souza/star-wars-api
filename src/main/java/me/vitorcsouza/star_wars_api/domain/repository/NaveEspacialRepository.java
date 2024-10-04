package me.vitorcsouza.star_wars_api.domain.repository;

import me.vitorcsouza.star_wars_api.domain.model.NaveEspacial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaveEspacialRepository extends JpaRepository<NaveEspacial, Long> {
}
