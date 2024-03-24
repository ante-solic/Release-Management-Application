package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, Long> {
}
