package me.vitorcsouza.star_wars_api.domain.service;

import me.vitorcsouza.star_wars_api.domain.dto.CadastroDto;

public interface LoginService {
    CadastroDto cadastro(CadastroDto dto, String senha);
}
