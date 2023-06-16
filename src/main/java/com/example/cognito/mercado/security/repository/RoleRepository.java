package com.example.cognito.mercado.security.repository;

import com.example.cognito.mercado.security.entity.Role;
import com.example.cognito.mercado.security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {


    Optional<Role> findByRoleName(RoleName roleName);
}
