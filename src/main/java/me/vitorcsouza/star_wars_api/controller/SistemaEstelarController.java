package me.vitorcsouza.star_wars_api.controller;

import jakarta.validation.Valid;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoRes;
import me.vitorcsouza.star_wars_api.domain.service.SistemaEstelarService;
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
public class SistemaEstelarController {
    @Autowired
    private SistemaEstelarService service;

    @PostMapping
    public ResponseEntity<SistemaEstelarDtoRes> create(@RequestBody @Valid SistemaEstelarDtoReq dtoReq, UriComponentsBuilder uri){
        SistemaEstelarDtoRes dtoRes = service.create(dtoReq);
        URI address = uri.path("/sistema-estelar/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SistemaEstelarDtoRes> findById(@PathVariable Long id){
        SistemaEstelarDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @GetMapping
    public ResponseEntity<Page<SistemaEstelarDtoRes>> findAll(@PageableDefault Pageable pageable){
        Page<SistemaEstelarDtoRes> dtoRes = service.findAll(pageable);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SistemaEstelarDtoRes> update(@PathVariable Long id, @RequestBody @Valid SistemaEstelarDtoReq dtoReq){
        SistemaEstelarDtoRes updated = service.update(dtoReq, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
