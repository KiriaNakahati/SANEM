package com.sanem.share.ong.repositories;

import com.sanem.share.ong.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String subject);
    Optional<User> findById(UUID id);
    Boolean existsByEmail(String email);
    void deleteById(UUID id);
}
