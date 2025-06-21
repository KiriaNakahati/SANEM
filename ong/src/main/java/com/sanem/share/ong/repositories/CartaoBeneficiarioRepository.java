package com.sanem.share.ong.repositories;

import com.sanem.share.ong.models.CartaoBeneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartaoBeneficiarioRepository extends JpaRepository<CartaoBeneficiario, UUID> {
    List<CartaoBeneficiario> findByBeneficiarioId(UUID beneficiarioId);
}
