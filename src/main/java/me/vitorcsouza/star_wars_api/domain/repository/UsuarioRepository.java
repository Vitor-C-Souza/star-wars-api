package me.vitorcsouza.star_wars_api.domain.repository;

import me.vitorcsouza.star_wars_api.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByUsuario(String username);

    boolean existsByUsuario(String usuario);
}
