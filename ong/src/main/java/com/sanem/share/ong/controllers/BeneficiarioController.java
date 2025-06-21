package com.sanem.share.ong.controllers;

import com.sanem.share.ong.dtos.ong.BeneficiarioRequestDTO;
import com.sanem.share.ong.dtos.ong.BeneficiarioResponseDTO;
import com.sanem.share.ong.dtos.ong.BeneficiarioUpdateDTO;
import com.sanem.share.ong.models.User;
import com.sanem.share.ong.services.BeneficiarioService;
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
@RequestMapping("/api/beneficiarios")
public class BeneficiarioController {

    private final BeneficiarioService beneficiarioService;

    public BeneficiarioController(BeneficiarioService beneficiarioService) {
        this.beneficiarioService = beneficiarioService;
    }

    @PostMapping
    public ResponseEntity<BeneficiarioResponseDTO> create(@RequestBody @Valid BeneficiarioRequestDTO dto) {
        User currentUser = (User)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
        beneficiarioService.create(dto, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<BeneficiarioResponseDTO>> findAll(
            @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(beneficiarioService.findAll(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BeneficiarioResponseDTO>> findAll() {
        return ResponseEntity.ok(beneficiarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiarioResponseDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(beneficiarioService.findById(id));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<BeneficiarioResponseDTO>> findByFuncionarioId(@PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(beneficiarioService.findByFuncionarioId(funcionarioId));
    }

    /**
     * Endpoint para atualizar um beneficiário
     */
    @PutMapping
    public ResponseEntity<BeneficiarioResponseDTO> update(@RequestBody @Valid BeneficiarioUpdateDTO dto) throws Exception {
        return ResponseEntity.ok(beneficiarioService.update(dto));
    }

    /**
     * Endpoint para excluir um beneficiário
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        beneficiarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}