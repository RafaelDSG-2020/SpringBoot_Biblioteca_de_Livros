package com.crud.cadastrousuario.domain.dto;


import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.Stock;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be at least 0")
    private Integer amount;

    private List<Book> books;

    public StockDTO(Stock stock) {

        this.id = stock.getId();
        this.amount = stock.getAmount();

    }
}
