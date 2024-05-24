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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable  {


    private Long id;

    private String name;

    private String email;

    private String phone;

    private Integer flag;



}
