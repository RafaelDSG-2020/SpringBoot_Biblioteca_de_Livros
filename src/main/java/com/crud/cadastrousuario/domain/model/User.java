package com.crud.cadastrousuario.domain.model;

import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Data
@Entity
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;
    @Column(name = "EMAIL", length = 50, nullable = false, unique=true )
    private String email;
    @Column(name = "PHONE" , length = 50, nullable = false)
    private String phone;
    @Column(name = "FLAG" , nullable = false)
    private Integer flag;

    @OneToMany(mappedBy = "usersID" , fetch = FetchType.EAGER)
    private List<LoanOfBooks> loan;


    public User(UserDTO userCreateDTO) {

        this.name = userCreateDTO.getName();
        this.email = userCreateDTO.getEmail();
        this.phone = userCreateDTO.getPhone();
        this.flag = 1;
    }
}
