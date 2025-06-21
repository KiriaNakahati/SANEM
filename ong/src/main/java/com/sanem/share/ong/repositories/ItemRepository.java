package com.sanem.share.ong.repositories;

import com.sanem.share.ong.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByFuncionarioId(UUID funcionarioId);
}
