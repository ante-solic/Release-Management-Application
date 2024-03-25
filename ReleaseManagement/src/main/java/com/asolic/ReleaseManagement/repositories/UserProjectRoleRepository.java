package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.models.UserProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRoleRepository extends JpaRepository<UserProjectRole, Long> {
    List<UserProjectRole> findByUserAndProject(User user, Project project);
    List<UserProjectRole> findByProject(Project project);
}
