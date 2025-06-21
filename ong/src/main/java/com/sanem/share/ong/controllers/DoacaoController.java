// Controller

package com.sanem.share.ong.controllers;

import com.sanem.share.ong.dtos.doacao.DoacaoRequestDTO;
import com.sanem.share.ong.dtos.doacao.DoacaoResponseDTO;
import com.sanem.share.ong.dtos.doacao.DoacaoUpdateDTO;
import com.sanem.share.ong.services.DoacaoService;
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
@RequestMapping("/api/doacoes")
public class DoacaoController {

    private final DoacaoService service;

    public DoacaoController(DoacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DoacaoResponseDTO> create(
            @RequestBody @Valid DoacaoRequestDTO dto) throws Exception {
        DoacaoResponseDTO resp = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp);
    }

    @GetMapping
    public ResponseEntity<Page<DoacaoResponseDTO>> findAll(
            @PageableDefault() Pageable pg) {
        return ResponseEntity.ok(service.findAll(pg));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoacaoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoacaoResponseDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<DoacaoResponseDTO> update(
            @RequestBody @Valid DoacaoUpdateDTO dto) throws Exception {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
