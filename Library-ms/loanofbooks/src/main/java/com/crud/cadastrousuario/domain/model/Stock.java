package com.crud.cadastrousuario.domain.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "STOCK")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable=false, updatable=false)
    private Long id;

    @Column(name = "AMOUNT" , nullable = false)
    private Integer amount;

    @OneToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;




}
