package com.example.cognito.mercado.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SignInDto {

    @NotEmpty(message = "Ingrese su email")
    private String email;

    @NotEmpty(message = "Ingrese su contraseña")
    private String password;

}
