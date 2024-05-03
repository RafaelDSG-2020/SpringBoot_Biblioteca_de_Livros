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
    private Long id;

    @Column(name = "AMOUNT" , nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    public Stock(StockDTO stockCreateDTO) {

        this.id = stockCreateDTO.getId();
        this.amount = stockCreateDTO.getAmount();

    }


}
