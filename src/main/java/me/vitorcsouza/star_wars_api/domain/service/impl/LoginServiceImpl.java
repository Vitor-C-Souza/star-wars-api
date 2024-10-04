package me.vitorcsouza.star_wars_api.domain.service.impl;

import me.vitorcsouza.star_wars_api.domain.dto.CadastroDto;
import me.vitorcsouza.star_wars_api.domain.model.Usuario;
import me.vitorcsouza.star_wars_api.domain.repository.UsuarioRepository;
import me.vitorcsouza.star_wars_api.domain.service.LoginService;
import me.vitorcsouza.star_wars_api.domain.service.convert.UsuarioConvert;
import me.vitorcsouza.star_wars_api.infra.exception.usuarioDuplicadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private UsuarioConvert convert;

    @Transactional
    @Override
    public CadastroDto cadastro(CadastroDto dto, String senha) {
        Usuario usuario = convert.toModel(dto.usuario(), senha);

        validacao(usuario);

        repository.save(usuario);
        return convert.toDto(usuario, dto.senha());
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsuario(username);
    }

    private void validacao(Usuario usuario) {
        boolean existe = repository.existsByUsuario(usuario.getUsuario());

        if (existe) {
            throw new usuarioDuplicadoException("Usuario ja existe!!!");
        }
    }
}
