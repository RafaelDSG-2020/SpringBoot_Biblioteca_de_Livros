package com.crud.cadastrousuario.domain.model;


import com.crud.cadastrousuario.domain.dto.StockDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "STOCK")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AMOUNT" , nullable = false)
    private Integer amount;

    @OneToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;


    public Stock(StockDTO stockCreateDTO) {


        this.amount = stockCreateDTO.getAmount();

    }


}
