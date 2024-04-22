package com.crud.cadastrousuario.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity(name = "users")
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOME", length = 50, nullable = false)
    private String name;
    @Column(name = "EMAIL", length = 50, nullable = false, unique=true )
    private String email;
    @Column(name = "TELEFONE" , length = 50, nullable = false)
    private String phone;




}
