package com.crud.cadastrousuario.domain.dto;


import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.Stock;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockDTO {



    private Long id;

    @NotNull(message = "quantity cannot be null")
    @NotBlank( message = "quantity cannot be empty")
    private Integer amount;

    private List<Book> books;

    public StockDTO(Stock stock) {

        this.id = stock.getId();
        this.amount = stock.getAmount();

    }
}
