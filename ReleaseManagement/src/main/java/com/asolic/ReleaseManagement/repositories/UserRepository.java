package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Page<User> findByUsernameContaining(String username, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.projects p WHERE p.id = :projectId")
    Page<User> findAllByProjectId(@Param("projectId") UUID projectId, Pageable pageable);

    @Query("SELECT u FROM User u LEFT JOIN u.projects p WHERE p IS NULL OR p.id <> :projectId")
    List<User> findAllNotInProject(@Param("projectId") UUID projectId);
}
