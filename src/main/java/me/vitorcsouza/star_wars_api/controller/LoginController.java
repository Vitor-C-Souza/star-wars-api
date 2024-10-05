package me.vitorcsouza.star_wars_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import me.vitorcsouza.star_wars_api.domain.dto.CadastroDto;
import me.vitorcsouza.star_wars_api.domain.dto.TokenJWT;
import me.vitorcsouza.star_wars_api.domain.model.Usuario;
import me.vitorcsouza.star_wars_api.domain.service.LoginService;
import me.vitorcsouza.star_wars_api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class LoginController {
    @Autowired
    private LoginService service;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Realiza um cadastro no sistema", tags = {"Autenticação"})
    public ResponseEntity<CadastroDto> cadastrar(@RequestBody @Valid CadastroDto dto, UriComponentsBuilder uri){
        String senhaEncriptada = BCrypt.hashpw(dto.senha(), BCrypt.gensalt());
        CadastroDto cadastro = service.cadastro(dto, senhaEncriptada);

        URI address = uri.path("/cadastrar/{id}").buildAndExpand(cadastro.id()).toUri();

        return ResponseEntity.created(address).body(cadastro);
    }

    @PostMapping("/logar")
    @Operation(summary = "Loga no sistema", tags = {"Autenticação"})
    public ResponseEntity<TokenJWT> logar(@RequestBody @Valid CadastroDto dto){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.usuario(), dto.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.GerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWT(tokenJWT));
    }
}
