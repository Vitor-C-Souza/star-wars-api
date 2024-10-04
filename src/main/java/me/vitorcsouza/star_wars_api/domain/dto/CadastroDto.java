package me.vitorcsouza.star_wars_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.vitorcsouza.star_wars_api.domain.model.Usuario;

public record CadastroDto(
        Long id,
        @NotBlank @NotNull String usuario,
        @NotBlank @NotNull String senha
) {
    public CadastroDto(Usuario cadastro, String senha) {
        this(
                cadastro.getId(),
                cadastro.getUsuario(),
                senha
        );
    }
}
