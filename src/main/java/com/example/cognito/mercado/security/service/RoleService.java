package com.example.cognito.mercado.security.service;

import com.example.cognito.mercado.security.entity.Role;
import com.example.cognito.mercado.security.enums.RoleName;
import com.example.cognito.mercado.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public void createRoles() {
        Role user = new Role(RoleName.ROLE_USER);
        Role admin = new Role(RoleName.ROLE_ADMIN);
        roleRepository.save(user);
        roleRepository.save(admin);
    }

    public Optional<Role> getRoleByRoleName(RoleName rolename){
        return roleRepository.findByRoleName(rolename);
    }
}
