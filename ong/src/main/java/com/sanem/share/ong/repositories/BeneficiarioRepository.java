package com.sanem.share.ong.repositories;

import com.sanem.share.ong.models.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, UUID> {
    List<Beneficiario> findByFuncionarioId(UUID funcionarioId);
}
