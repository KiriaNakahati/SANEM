package com.sanem.share.ong.controllers;

import com.sanem.share.ong.dtos.beneficiario.CartaoBeneficiarioRequestDTO;
import com.sanem.share.ong.dtos.beneficiario.CartaoBeneficiarioResponseDTO;
import com.sanem.share.ong.dtos.beneficiario.CartaoBeneficiarioUpdateDTO;
import com.sanem.share.ong.services.CartaoBeneficiarioService;
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
@RequestMapping("/api/cartoes")
public class CartaoBeneficiarioController {

    private final CartaoBeneficiarioService service;

    public CartaoBeneficiarioController(CartaoBeneficiarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CartaoBeneficiarioRequestDTO dto) throws Exception {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<CartaoBeneficiarioResponseDTO>> findAll(
            @PageableDefault() Pageable pg) {
        return ResponseEntity.ok(service.findAll(pg));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartaoBeneficiarioResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoBeneficiarioResponseDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/beneficiario/{benId}")
    public ResponseEntity<List<CartaoBeneficiarioResponseDTO>> findByBeneficiarioId(
            @PathVariable("benId") UUID benId) {
        return ResponseEntity.ok(service.findByBeneficiarioId(benId));
    }

    @PutMapping
    public ResponseEntity<CartaoBeneficiarioResponseDTO> update(
            @RequestBody @Valid CartaoBeneficiarioUpdateDTO dto) throws Exception {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}