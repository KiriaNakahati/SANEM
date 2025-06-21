package com.sanem.share.ong.repositories;

import com.sanem.share.ong.models.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, UUID> {
    List<Relatorio> findByFuncionarioId(UUID funcionarioId);
}
