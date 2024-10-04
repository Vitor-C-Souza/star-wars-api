package me.vitorcsouza.star_wars_api.controller;

import jakarta.validation.Valid;
import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.PlanetaDtoRes;
import me.vitorcsouza.star_wars_api.domain.service.PlanetaService;
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
public class PlanetaController {
    @Autowired
    private PlanetaService service;

    @PostMapping
    public ResponseEntity<PlanetaDtoRes> create(@RequestBody @Valid PlanetaDtoReq dtoReq, UriComponentsBuilder uri){
        PlanetaDtoRes dtoRes = service.create(dtoReq);
        URI address = uri.path("/planeta/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    public ResponseEntity<Page<PlanetaDtoRes>> findAll(@PageableDefault Pageable pageable){
        Page<PlanetaDtoRes> dtoRes = service.findAll(pageable);
        return ResponseEntity.ok(dtoRes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetaDtoRes> findById(@PathVariable Long id){
        PlanetaDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanetaDtoRes> update(@PathVariable Long id, @RequestBody @Valid PlanetaDtoReq dtoReq){
        PlanetaDtoRes updated = service.update(dtoReq, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
