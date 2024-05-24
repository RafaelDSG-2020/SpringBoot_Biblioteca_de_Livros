package com.crud.cadastrousuario.domain.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable=false, updatable=false)
    private Long id;

    @Column(name = "AMOUNT" , nullable = false)
    private Integer amount;

    @Column(name = "BOOK_ID")
    private Long book;




}
