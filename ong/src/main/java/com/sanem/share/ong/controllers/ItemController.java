package com.sanem.share.ong.controllers;

import com.sanem.share.ong.dtos.item.ItemRequestDTO;
import com.sanem.share.ong.dtos.item.ItemResponseDTO;
import com.sanem.share.ong.dtos.item.ItemUpdateDTO;
import com.sanem.share.ong.models.User;
import com.sanem.share.ong.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ItemRequestDTO dto) throws Exception {
        User currentUser = (User)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
        service.create(dto, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponseDTO>> findAll(
            @PageableDefault() Pageable pg) {
        return ResponseEntity.ok(service.findAll(pg));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/funcionario/{funcId}")
    public ResponseEntity<List<ItemResponseDTO>> findByFuncionarioId(
            @PathVariable("funcId") UUID funcId) {
        return ResponseEntity.ok(service.findByFuncionarioId(funcId));
    }

    @PutMapping
    public ResponseEntity<ItemResponseDTO> update(
            @RequestBody @Valid ItemUpdateDTO dto) throws Exception {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
