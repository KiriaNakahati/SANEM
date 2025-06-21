package com.sanem.share.ong.services;

import com.sanem.share.ong.dtos.beneficiario.CartaoBeneficiarioRequestDTO;
import com.sanem.share.ong.dtos.beneficiario.CartaoBeneficiarioResponseDTO;
import com.sanem.share.ong.dtos.beneficiario.CartaoBeneficiarioUpdateDTO;
import com.sanem.share.ong.models.Beneficiario;
import com.sanem.share.ong.models.CartaoBeneficiario;
import com.sanem.share.ong.repositories.BeneficiarioRepository;
import com.sanem.share.ong.repositories.CartaoBeneficiarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartaoBeneficiarioService {

    private final CartaoBeneficiarioRepository repo;
    private final BeneficiarioRepository benRepo;

    public CartaoBeneficiarioService(CartaoBeneficiarioRepository repo,
                                     BeneficiarioRepository benRepo) {
        this.repo = repo;
        this.benRepo = benRepo;
    }

    @Transactional
    public CartaoBeneficiarioResponseDTO create(CartaoBeneficiarioRequestDTO dto) throws Exception {
        Beneficiario ben = benRepo.findById(dto.beneficiarioId())
                .orElseThrow(() -> new Exception("Beneficiário não encontrado"));
        CartaoBeneficiario c = new CartaoBeneficiario();
        c.setBeneficiario(ben);
        c.setDataEmissao(dto.dataEmissao());
        CartaoBeneficiario saved = repo.save(c);
        return new CartaoBeneficiarioResponseDTO(saved);
    }

    public Page<CartaoBeneficiarioResponseDTO> findAll(Pageable pg) {
        return repo.findAll(pg).map(CartaoBeneficiarioResponseDTO::new);
    }

    public List<CartaoBeneficiarioResponseDTO> findAll() {
        return repo.findAll()
                .stream()
                .map(CartaoBeneficiarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CartaoBeneficiarioResponseDTO findById(UUID id) throws Exception {
        return repo.findById(id)
                .map(CartaoBeneficiarioResponseDTO::new)
                .orElseThrow(() -> new Exception("Cartão não encontrado"));
    }

    public List<CartaoBeneficiarioResponseDTO> findByBeneficiarioId(UUID benId) {
        return repo.findByBeneficiarioId(benId)
                .stream()
                .map(CartaoBeneficiarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartaoBeneficiarioResponseDTO update(CartaoBeneficiarioUpdateDTO dto) throws Exception {
        CartaoBeneficiario c = repo.findById(dto.id())
                .orElseThrow(() -> new Exception("Cartão não encontrado"));
        Beneficiario ben = benRepo.findById(dto.beneficiarioId())
                .orElseThrow(() -> new Exception("Beneficiário não encontrado"));
        c.setBeneficiario(ben);
        c.setDataEmissao(dto.dataEmissao());
        CartaoBeneficiario updated = repo.save(c);
        return new CartaoBeneficiarioResponseDTO(updated);
    }

    @Transactional
    public void delete(UUID id) throws Exception {
        if (!repo.existsById(id)) throw new Exception("Cartão não encontrado");
        repo.deleteById(id);
    }
}