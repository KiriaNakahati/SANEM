package com.sanem.share.ong.controllers;

import com.sanem.share.ong.dtos.item_doacao.ItemDoacaoRequestDTO;
import com.sanem.share.ong.dtos.item_doacao.ItemDoacaoResponseDTO;
import com.sanem.share.ong.dtos.item_doacao.ItemDoacaoUpdateDTO;
import com.sanem.share.ong.services.ItemDoacaoService;
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
@RequestMapping("/api/item-doacoes")
public class ItemDoacaoController {

    private final ItemDoacaoService service;

    public ItemDoacaoController(ItemDoacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ItemDoacaoRequestDTO dto) throws Exception {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<ItemDoacaoResponseDTO>> findAll(
            @PageableDefault() Pageable pg) {
        return ResponseEntity.ok(service.findAll(pg));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemDoacaoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDoacaoResponseDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/doacao/{doId}")
    public ResponseEntity<List<ItemDoacaoResponseDTO>> findByDoacaoId(
            @PathVariable("doId") UUID doId) {
        return ResponseEntity.ok(service.findByDoacaoId(doId));
    }

    @PutMapping
    public ResponseEntity<ItemDoacaoResponseDTO> update(
            @RequestBody @Valid ItemDoacaoUpdateDTO dto) throws Exception {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
