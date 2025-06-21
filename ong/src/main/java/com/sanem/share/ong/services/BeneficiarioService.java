package com.sanem.share.ong.services;

import com.sanem.share.ong.dtos.ong.BeneficiarioRequestDTO;
import com.sanem.share.ong.dtos.ong.BeneficiarioResponseDTO;
import com.sanem.share.ong.dtos.ong.BeneficiarioUpdateDTO;
import com.sanem.share.ong.models.Beneficiario;
import com.sanem.share.ong.models.User;
import com.sanem.share.ong.repositories.BeneficiarioRepository;
import com.sanem.share.ong.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BeneficiarioService {

    private final BeneficiarioRepository beneficiarioRepository;
    private final UserRepository funcionarioRepository;

    public BeneficiarioService(BeneficiarioRepository beneficiarioRepository,
                               UserRepository funcionarioRepository) {
        this.beneficiarioRepository = beneficiarioRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Cria um novo beneficiário
     *
     * @param dto Dados do beneficiário a ser criado
     * @return DTO com os dados do beneficiário criado
     */
    @Transactional
    public BeneficiarioResponseDTO create(BeneficiarioRequestDTO dto, User funcionario) {
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setTelefone(dto.telefone());
        beneficiario.setNome(dto.nome());
        beneficiario.setFuncionario(funcionario);

        Beneficiario savedBeneficiario = beneficiarioRepository.save(beneficiario);
        return new BeneficiarioResponseDTO(savedBeneficiario);
    }

    public Page<BeneficiarioResponseDTO> findAll(Pageable pageable) {
        return beneficiarioRepository.findAll(pageable)
                .map(BeneficiarioResponseDTO::new);
    }

    public List<BeneficiarioResponseDTO> findAll() {
        return beneficiarioRepository.findAll().stream()
                .map(BeneficiarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public BeneficiarioResponseDTO findById(UUID id) throws Exception {
        Beneficiario beneficiario = beneficiarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Beneficiário não encontrado"));

        return new BeneficiarioResponseDTO(beneficiario);
    }

    public List<BeneficiarioResponseDTO> findByFuncionarioId(UUID funcionarioId) {
        return beneficiarioRepository.findByFuncionarioId(funcionarioId).stream()
                .map(BeneficiarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BeneficiarioResponseDTO update(BeneficiarioUpdateDTO dto) throws Exception {
        Beneficiario beneficiario = beneficiarioRepository.findById(dto.id())
                .orElseThrow(() -> new Exception("Beneficiário não encontrado"));

        User funcionario = funcionarioRepository.findById(dto.funcionarioId())
                .orElseThrow(() -> new Exception("Funcionário não encontrado"));

        beneficiario.setTelefone(dto.telefone());
        beneficiario.setFuncionario(funcionario);

        Beneficiario updatedBeneficiario = beneficiarioRepository.save(beneficiario);
        return new BeneficiarioResponseDTO(updatedBeneficiario);
    }

    @Transactional
    public void delete(UUID id) throws Exception {
        if (!beneficiarioRepository.existsById(id)) {
            throw new Exception("Beneficiário não encontrado");
        }

        beneficiarioRepository.deleteById(id);
    }
}