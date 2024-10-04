package me.vitorcsouza.star_wars_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoRes;
import me.vitorcsouza.star_wars_api.domain.service.NaveEspacialService;
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
@RequestMapping("/nave-espacial")
@SecurityRequirement(name = "bearer-key")
public class NaveEspacialController {
    @Autowired
    private NaveEspacialService service;

    @PostMapping
    @Operation(summary = "Criar uma nova nave espacial", tags = {"Nave Espacial"})
    public ResponseEntity<NaveEspacialDtoRes> create(@RequestBody @Valid NaveEspacialDtoReq dtoReq, UriComponentsBuilder uri){
        NaveEspacialDtoRes dtoRes = service.create(dtoReq);
        URI address = uri.path("/nave-espacial/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    @Operation(summary = "Listar todas as naves espaciais.", tags = {"Nave Espacial"})
    public ResponseEntity<Page<NaveEspacialDtoRes>> findAll(@PageableDefault @ParameterObject Pageable pageable){
        Page<NaveEspacialDtoRes> dtoResPage = service.findAll(pageable);
        return ResponseEntity.ok(dtoResPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de uma nave espacial específica.", tags = {"Nave Espacial"})
    public ResponseEntity<NaveEspacialDtoRes> findById(@PathVariable Long id){
        NaveEspacialDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações de uma nave espacial.", tags = {"Nave Espacial"})
    public ResponseEntity<NaveEspacialDtoRes> update(@PathVariable Long id, @RequestBody @Valid NaveEspacialDtoReq dtoReq){
        NaveEspacialDtoRes updated = service.update(dtoReq, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma nave espacial.", tags = {"Nave Espacial"})
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
