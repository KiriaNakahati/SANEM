package com.sanem.share.ong.controllers;

import com.sanem.share.ong.dtos.relatorio.RelatorioRequestDTO;
import com.sanem.share.ong.dtos.relatorio.RelatorioResponseDTO;
import com.sanem.share.ong.dtos.relatorio.RelatorioUpdateDTO;
import com.sanem.share.ong.services.RelatorioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid RelatorioRequestDTO dto) throws Exception {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<RelatorioResponseDTO>> findAll(
            @PageableDefault() Pageable pg) {
        return ResponseEntity.ok(service.findAll(pg));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RelatorioResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatorioResponseDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/funcionario/{funcId}")
    public ResponseEntity<List<RelatorioResponseDTO>> findByFuncionarioId(
            @PathVariable("funcId") UUID funcId) {
        return ResponseEntity.ok(service.findByFuncionarioId(funcId));
    }

    @PutMapping
    public ResponseEntity<RelatorioResponseDTO> update(
            @RequestBody @Valid RelatorioUpdateDTO dto) throws Exception {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
