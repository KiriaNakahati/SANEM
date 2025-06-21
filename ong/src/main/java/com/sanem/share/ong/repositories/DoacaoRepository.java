package com.sanem.share.ong.repositories;

import com.sanem.share.ong.models.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoacaoRepository  extends JpaRepository<Doacao, UUID> {
}
