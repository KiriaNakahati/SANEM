package com.sanem.share.ong.services;

import com.sanem.share.ong.dtos.relatorio.RelatorioRequestDTO;
import com.sanem.share.ong.dtos.relatorio.RelatorioResponseDTO;
import com.sanem.share.ong.dtos.relatorio.RelatorioUpdateDTO;
import com.sanem.share.ong.models.Relatorio;
import com.sanem.share.ong.models.User;
import com.sanem.share.ong.repositories.RelatorioRepository;
import com.sanem.share.ong.repositories.UserRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final RelatorioRepository repo;
    private final UserRepository userRepo;

    public RelatorioService(RelatorioRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Transactional
    public RelatorioResponseDTO create(RelatorioRequestDTO dto) throws Exception {
        User user = userRepo.findById(dto.funcionarioId())
                .orElseThrow(() -> new Exception("Funcionário não encontrado"));
        Relatorio relatorio = new Relatorio();
        relatorio.setFuncionario(user);
        relatorio.setDataEmissao(dto.dataEmissao());
        return new RelatorioResponseDTO(repo.save(relatorio));
    }

    public Page<RelatorioResponseDTO> findAll(Pageable pg) {
        return repo.findAll(pg).map(RelatorioResponseDTO::new);
    }

    public List<RelatorioResponseDTO> findAll() {
        return repo.findAll().stream().map(RelatorioResponseDTO::new).collect(Collectors.toList());
    }

    public RelatorioResponseDTO findById(UUID id) throws Exception {
        return repo.findById(id)
                .map(RelatorioResponseDTO::new)
                .orElseThrow(() -> new Exception("Relatório não encontrado"));
    }

    public List<RelatorioResponseDTO> findByFuncionarioId(UUID funcId) {
        return repo.findByFuncionarioId(funcId)
                .stream()
                .map(RelatorioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public RelatorioResponseDTO update(RelatorioUpdateDTO dto) throws Exception {
        Relatorio relatorio = repo.findById(dto.id())
                .orElseThrow(() -> new Exception("Relatório não encontrado"));
        User user = userRepo.findById(dto.funcionarioId())
                .orElseThrow(() -> new Exception("Funcionário não encontrado"));
        relatorio.setFuncionario(user);
        relatorio.setDataEmissao(dto.dataEmissao());
        return new RelatorioResponseDTO(repo.save(relatorio));
    }

    @Transactional
    public void delete(UUID id) throws Exception {
        if (!repo.existsById(id)) throw new Exception("Relatório não encontrado");
        repo.deleteById(id);
    }
}
