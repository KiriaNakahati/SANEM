package com.sanem.share.ong.repositories;

import com.sanem.share.ong.models.ItemDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemDoacaoRepository extends JpaRepository<ItemDoacao, UUID> {
    List<ItemDoacao> findByDoacaoId(UUID doacaoId);
}
