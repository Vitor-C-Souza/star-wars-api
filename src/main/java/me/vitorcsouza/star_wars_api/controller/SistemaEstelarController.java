package me.vitorcsouza.star_wars_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoRes;
import me.vitorcsouza.star_wars_api.domain.service.SistemaEstelarService;
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
@RequestMapping("/sistema-estelar")
@SecurityRequirement(name = "bearer-key")
public class SistemaEstelarController {
    @Autowired
    private SistemaEstelarService service;

    @PostMapping
    @Operation(summary = "Criar um novo sistema estelar.", tags = {"Sistema Estelar"})
    public ResponseEntity<SistemaEstelarDtoRes> create(@RequestBody @Valid SistemaEstelarDtoReq dtoReq, UriComponentsBuilder uri){
        SistemaEstelarDtoRes dtoRes = service.create(dtoReq);
        URI address = uri.path("/sistema-estelar/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de um sistema estelar específico.", tags = {"Sistema Estelar"})
    public ResponseEntity<SistemaEstelarDtoRes> findById(@PathVariable Long id){
        SistemaEstelarDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @GetMapping
    @Operation(summary = "Listar todos os sistemas estelares.", tags = {"Sistema Estelar"})
    public ResponseEntity<Page<SistemaEstelarDtoRes>> findAll(@PageableDefault @ParameterObject Pageable pageable){
        Page<SistemaEstelarDtoRes> dtoRes = service.findAll(pageable);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações de um sistema estelar.", tags = {"Sistema Estelar"})
    public ResponseEntity<SistemaEstelarDtoRes> update(@PathVariable Long id, @RequestBody @Valid SistemaEstelarDtoReq dtoReq){
        SistemaEstelarDtoRes updated = service.update(dtoReq, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um sistema estelar.", tags = {"Sistema Estelar"})
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
