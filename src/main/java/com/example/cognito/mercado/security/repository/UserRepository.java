package com.example.cognito.mercado.security.repository;

import com.example.cognito.mercado.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String userEmail);
    Optional<User> findByName(String name);
}
