package me.vitorcsouza.star_wars_api.domain.service.convert;

import me.vitorcsouza.star_wars_api.domain.dto.CadastroDto;
import me.vitorcsouza.star_wars_api.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConvert {
    public Usuario toModel(String usuario, String senha){
        return new Usuario(usuario, senha);
    }

    public CadastroDto toDto(Usuario usuario, String senha){
        return new CadastroDto(usuario, senha);
    }
}
