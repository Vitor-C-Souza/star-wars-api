package me.vitorcsouza.star_wars_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoRes;
import me.vitorcsouza.star_wars_api.domain.service.PlanetaService;
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
@RequestMapping("/planeta")
@SecurityRequirement(name = "bearer-key")
public class PlanetaController {
    @Autowired
    private PlanetaService service;

    @PostMapping
    @Operation(summary = "Criar um novo planeta.", tags = {"Planeta"})
    public ResponseEntity<PlanetaDtoRes> create(@RequestBody @Valid PlanetaDtoReq dtoReq, UriComponentsBuilder uri){
        PlanetaDtoRes dtoRes = service.create(dtoReq);
        URI address = uri.path("/planeta/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    @Operation(summary = "Listar todos os planetas.", tags = {"Planeta"})
    public ResponseEntity<Page<PlanetaDtoRes>> findAll(@PageableDefault @ParameterObject Pageable pageable){
        Page<PlanetaDtoRes> dtoRes = service.findAll(pageable);
        return ResponseEntity.ok(dtoRes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de um planeta específico.", tags = {"Planeta"})
    public ResponseEntity<PlanetaDtoRes> findById(@PathVariable Long id){
        PlanetaDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações de um planeta.", tags = {"Planeta"})
    public ResponseEntity<PlanetaDtoRes> update(@PathVariable Long id, @RequestBody @Valid PlanetaDtoReq dtoReq){
        PlanetaDtoRes updated = service.update(dtoReq, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um planeta.", tags = {"Planeta"})
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
