package com.example.cognito.mercado.security.dto;

import com.example.cognito.mercado.security.entity.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignupDto {


    @NotEmpty(message = "El nombre es obligario")
    private String name;

    @NotEmpty(message = "El apellido es obligario")
    private String lastName;

    @NotEmpty(message = "El password es obligario")
    private String password;

    @NotEmpty(message = "El email es obligario")
    private String email;


    private Role role;
}
