package com.example.cognito.mercado.security.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotEmpty(message = "El nombre es obligario")
    private String name;

    @NotEmpty(message = "El apellido es obligario")
    private String lastName;

    @NotEmpty(message = "El email es obligario")
    private String email;

    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Role role;
}
