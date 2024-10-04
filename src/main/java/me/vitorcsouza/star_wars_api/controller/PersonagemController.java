package me.vitorcsouza.star_wars_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoRes;
import me.vitorcsouza.star_wars_api.domain.service.PersonagemService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/personagem")
@SecurityRequirement(name = "bearer-key")
public class PersonagemController {
    @Autowired
    private PersonagemService service;

    @PostMapping
    @Operation(summary = "Criar um novo personagem.", tags = {"Personagem"})
    public ResponseEntity<PersonagemDtoRes> create(@RequestBody @Valid PersonagemDtoReq dtoReq, UriComponentsBuilder uri){
        PersonagemDtoRes dtoRes = service.create(dtoReq);
        URI address = uri.path("/personagem/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    @Operation(summary = "Listar todos os personagens.", tags = {"Personagem"})
    public ResponseEntity<Page<PersonagemDtoRes>> findAll(@PageableDefault @ParameterObject Pageable pageable){
        Page<PersonagemDtoRes> dtoResPage = service.findAll(pageable);
        return ResponseEntity.ok(dtoResPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de um personagem específico.", tags = {"Personagem"})
    public ResponseEntity<PersonagemDtoRes> findById(@PathVariable Long id){
        PersonagemDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações de um personagem.", tags = {"Personagem"})
    public ResponseEntity<PersonagemDtoRes> update(@PathVariable Long id, @RequestBody @Valid PersonagemDtoReq dtoReq){
        PersonagemDtoRes updated = service.update(dtoReq, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um personagem.", tags = {"Personagem"})
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
