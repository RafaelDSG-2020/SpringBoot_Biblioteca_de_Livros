package com.crud.cadastrousuario.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;



@Data
@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID" , insertable=false, updatable=false)
    private Long id;
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;
    @Column(name = "EMAIL", length = 50, nullable = false, unique=true )
    private String email;
    @Column(name = "PHONE" , length = 50, nullable = false)
    private String phone;
    @Column(name = "FLAG" , nullable = false)
    private Integer flag;

    @JsonIgnore
    @OneToMany(mappedBy = "usersID" , fetch = FetchType.LAZY)
    private List<LoanOfBooks> loan;


}
