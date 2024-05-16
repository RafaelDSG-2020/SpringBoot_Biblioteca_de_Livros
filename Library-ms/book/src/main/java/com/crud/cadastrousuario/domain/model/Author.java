package com.crud.cadastrousuario.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "AUTHOR")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID" , insertable=false, updatable=false)
    private Long id;
    @Column(name = "NAME" , length = 50, nullable = false)
    private String name;
    @Column(name = "NATIONALITY" , length = 50, nullable = false)
    private String nationality;

    @Column(name = "FLAG" , nullable = false)
    private Integer flag;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "authors" , cascade = CascadeType.ALL)
//    private List<Book> books = new ArrayList<>();



}
