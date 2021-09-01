package com.epam.tc.news.repository;

import com.epam.tc.news.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
